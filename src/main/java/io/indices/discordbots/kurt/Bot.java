package io.indices.discordbots.kurt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.indices.discordbots.kurt.command.CommandModule;
import io.indices.discordbots.kurt.config.Config;
import io.indices.discordbots.kurt.config.ConfigModule;
import io.indices.discordbots.kurt.db.HibernateDbModule;
import io.indices.discordbots.kurt.listener.CommandListener;
import io.indices.discordbots.kurt.listener.CorrectionListener;
import io.indices.discordbots.kurt.listener.LoggingListener;
import io.indices.discordbots.kurt.rest.RestApiModule;
import io.indices.discordbots.kurt.scheduler.RegionChangeScheduler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.security.auth.login.LoginException;
import lombok.extern.java.Log;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;

@Log
public class Bot {

    public static String CHAT_PREFIX = "!";

    private Injector injector;

    private Gson gson;
    private JDA jda;
    private Config config;

    private ConfigModule configModule;
    @Inject
    private CommandModule commandModule;
    @Inject
    private HibernateDbModule hibernateDbModule;

    @Inject
    private RegionChangeScheduler regionChangeScheduler;
    @Inject
    private RestApiModule restApiModule;

    public static final List<String> botAdmins = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Bot bot = new Bot();

        try {
            bot.start();
        } catch (LoginException e) {
            log.log(Level.SEVERE, "Error logging into Discord using provided token - shutting down!", e);
        }
    }

    private void start() throws LoginException, InterruptedException {
        String version = Bot.class.getPackage().getImplementationVersion();
        Path currentDir = Paths.get(".").toAbsolutePath().normalize();

        gson = new GsonBuilder().setPrettyPrinting()
          .setVersion(Double.parseDouble(version))
          .create();

        configModule = new ConfigModule(gson, currentDir);
        configModule.enable();
        this.config = configModule.getConfig();

        jda = new JDABuilder(AccountType.BOT)
          .setToken(config.token)
          .setGame(Game.playing(config.status))
          .setEventManager(new AnnotatedEventManager())
          .buildBlocking();

        injector = Guice.createInjector(new BotModule(this, version, currentDir, config, jda));
        injector.injectMembers(this);

        //hibernateDbModule.enable();

        commandModule.enable();
        restApiModule.enable();

        botAdmins.add("109976255725621248");

        registerListeners();

        log.info("Kurt start complete!");
    }

    private void registerListeners() {
        jda.addEventListener(injector.getInstance(CommandListener.class));
        jda.addEventListener(injector.getInstance(CorrectionListener.class));
        jda.addEventListener(injector.getInstance(LoggingListener.class));

        //regionChangeScheduler.scheduleRegionChanger(config.modules().timeRegionChanger, jda);
    }
}
