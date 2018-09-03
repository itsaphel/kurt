package io.indices.discordbots.kurt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import io.indices.discordbots.kurt.commands.CommandManager;
import io.indices.discordbots.kurt.listeners.CommandListener;
import io.indices.discordbots.kurt.listeners.CorrectionListener;
import io.indices.discordbots.kurt.rest.UrbanDictionaryApi;
import io.indices.discordbots.kurt.rest.WolframApi;
import io.indices.discordbots.kurt.schedulers.RegionChangeScheduler;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;

public class Bot {

    public static String CHAT_PREFIX = "!";
    public static Logger TEST_LOGGER = Logger.getLogger("TEST LOGGER");
    public static Path currentDir;
    private static final Logger logger = Logger.getLogger(Bot.class.getName());

    private Gson gson;
    private Config config;
    private JDA jda;

    private CommandManager commandManager;
    private RegionChangeScheduler regionChangeScheduler;
    private WolframApi wolframApi;
    private UrbanDictionaryApi urbanDictionaryApi;

    public static final List<String> botAdmins = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Bot bot = new Bot();
        bot.bootstrap();
        try {
            bot.start();
        } catch (LoginException e) {
            logger.log(Level.SEVERE,
              "Error logging into Discord using provided token - shutting down!", e);
        }
    }

    private void start() throws LoginException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT)
          .setToken(config.getToken())
          .setGame(Game.playing(config.getStatus()))
          .setEventManager(new AnnotatedEventManager())
          .buildBlocking();

        wolframApi = new WolframApi(this, config.getApis().getWolframAlphaApiKey());
        urbanDictionaryApi = new UrbanDictionaryApi();
        try {
            urbanDictionaryApi.loadBlacklistedWords();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error loading blacklisted words for Urban Dictionary.");
        }

        registerCommands();
        registerListeners();

        botAdmins.add("109976255725621248");

        logger.info("Kurt start complete!");
    }

    private void bootstrap() {
        gson = new GsonBuilder().setPrettyPrinting()
          .setVersion(Double.parseDouble(Bot.class.getPackage().getImplementationVersion()))
          .create();

        try {
            currentDir = Paths.get(".").toAbsolutePath().normalize();
            config = gson.fromJson(
              new JsonReader(new FileReader(currentDir.resolve("config.json").toFile())),
              Config.class);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Could not load config.json - shutting down!", e);
            System.exit(1);
        }
    }

    private void registerCommands() {
        commandManager = new CommandManager(this);
        commandManager.registerCommands();

        logger.finer("Registered commands.");
    }

    private void registerListeners() {
        jda.addEventListener(new CommandListener(this));
        jda.addEventListener(new CorrectionListener(this));

        regionChangeScheduler = new RegionChangeScheduler();
        regionChangeScheduler
          .scheduleRegionChanger(config.getModules().getTimeRegionChanger(), jda);

        logger.finer("Registered listeners.");
    }

    public JDA getJda() {
        return jda;
    }

    public Config getConfig() {
        return config;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public RegionChangeScheduler getRegionChangeScheduler() {
        return regionChangeScheduler;
    }

    public WolframApi getWolframApi() {
        return wolframApi;
    }

    public UrbanDictionaryApi getUrbanDictionaryApi() {
        return urbanDictionaryApi;
    }
}
