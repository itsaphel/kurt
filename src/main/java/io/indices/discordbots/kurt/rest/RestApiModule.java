package io.indices.discordbots.kurt.rest;

import io.indices.discordbots.kurt.module.IModule;
import java.io.IOException;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.java.Log;

@Log
@Singleton
public class RestApiModule implements IModule {

    @Inject
    private WolframApi wolframApi;
    @Inject
    private UrbanDictionaryApi urbanDictionaryApi;

    @Override
    public void enable() {
        try {
            urbanDictionaryApi.loadBlacklistedWords();
        } catch (IOException e) {
            log.log(Level.WARNING, "Error loading blacklisted words for Urban Dictionary.");
        }
    }

    @Override
    public void disable() {

    }
}
