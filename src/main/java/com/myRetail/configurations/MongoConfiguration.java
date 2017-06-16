package com.myRetail.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class MongoConfiguration extends Configuration{

    @JsonProperty("mongosHosts")
    @NotNull
    private List<String> mongosHosts;

    @JsonProperty("port")
    @NotNull
    private int port;

    @JsonProperty("database")
    @NotNull
    private String database;

    public List<String> getMongosHosts() {
        return mongosHosts;
    }

    public void setMongosHosts(List<String> mongosHosts) {
        this.mongosHosts = mongosHosts;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

}
