package io.indices.discordbots.kurt.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.indices.discordbots.kurt.module.IModule;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.logging.Level;
import lombok.extern.java.Log;

@Log
public class ConfigModule implements IModule {

    private Gson gson;
    private Path currentDir;

    private Config config;

    public ConfigModule(Gson gson, Path currentDir) {
        this.gson = gson;
        this.currentDir = currentDir;
    }

    @Override
    public void enable() {
        try {
            config = gson.fromJson(
              new JsonReader(new FileReader(currentDir.resolve("config.json").toFile())),
              Config.class);
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "Could not load config.json - shutting down!", e);
            System.exit(1);
        }
    }

    @Override
    public void disable() {

    }

    public Config getConfig() {
        return config;
    }
}
