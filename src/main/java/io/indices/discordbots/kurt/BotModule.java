package io.indices.discordbots.kurt;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.nio.file.Path;
import java.util.logging.Logger;

public class BotModule extends AbstractModule {

    private Bot bot;
    private String version;
    private Path currentDir;
    private Logger testLogger = Logger.getLogger("TEST LOGGER");

    public BotModule(Bot bot, String version, Path currentDir) {
        this.bot = bot;
        this.version = version;
        this.currentDir = currentDir;
    }

    @Override
    protected void configure() {
        bind(Bot.class).toInstance(bot);
        bind(String.class).annotatedWith(Names.named("BotVersion")).toInstance(version);
        bind(Path.class).annotatedWith(Names.named("RunDir")).toInstance(currentDir);
        bind(Logger.class).annotatedWith(Names.named("TestLogger")).toInstance(testLogger);
    }
}
