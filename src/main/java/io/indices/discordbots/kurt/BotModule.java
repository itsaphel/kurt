package io.indices.discordbots.kurt;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import io.indices.discordbots.kurt.config.Config;
import java.nio.file.Path;
import java.util.logging.Logger;
import net.dv8tion.jda.core.JDA;

public class BotModule extends AbstractModule {

    private Bot bot;
    private String version;
    private Path currentDir;
    private Config config;
    private JDA jda;
    private Logger testLogger = Logger.getLogger("TEST LOGGER");

    public BotModule(Bot bot, String version, Path currentDir, Config config, JDA jda) {
        this.bot = bot;
        this.version = version;
        this.currentDir = currentDir;
        this.config = config;
        this.jda = jda;
    }

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("BotVersion")).toInstance(version);
        bind(Path.class).annotatedWith(Names.named("RunDir")).toInstance(currentDir);
        bind(Logger.class).annotatedWith(Names.named("TestLogger")).toInstance(testLogger);
        bind(Config.class).toInstance(config);
        bind(JDA.class).toInstance(jda);
        bind(Bot.class).toInstance(bot);
    }
}
