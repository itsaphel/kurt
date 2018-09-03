package io.indices.discordbots.kurt.config;

import com.google.gson.annotations.Since;

public class Config {

    @Since(0.1)
    public String token;

    @Since(0.1)
    public String status;

    @Since(0.1)
    private Modules modules;

    @Since(0.1)
    private APIs apis;

    @Since(0.2)
    private Db db;

    public Modules modules() {
        return modules;
    }

    public APIs apis() {
        return apis;
    }

    public Db db() {
        return db;
    }
}