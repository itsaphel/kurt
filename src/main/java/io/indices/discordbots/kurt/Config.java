package io.indices.discordbots.kurt;

import com.google.gson.annotations.Since;
import java.util.Map;

class Config {

    @Since(0.1)
    private String token;

    @Since(0.1)
    private String status;

    @Since(0.1)
    private Modules modules;

    public String getToken() {
        return token;
    }

    public String getStatus() {
        return status;
    }

    public Modules getModules() {
        return modules;
    }
}

class Modules {

    @Since(0.1)
    private Map<String, String> timeRegionChanger;

    public Map<String, String> getTimeRegionChanger() {
        return timeRegionChanger;
    }
}