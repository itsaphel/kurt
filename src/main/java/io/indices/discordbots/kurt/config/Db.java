package io.indices.discordbots.kurt.config;

import com.google.gson.annotations.Since;

public class Db {

    @Since(0.2)
    public String username;

    @Since(0.2)
    public String password;

    @Since(0.2)
    public String driver;

    @Since(0.2)
    public String url;

    @Since(0.2)
    public String dialect;

    @Since(0.2)
    public int poolSize;

    @Since(0.2)
    public boolean initialTableCreation;
}
