package com.myRetail.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.myRetail.configurations.MongoConfiguration;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class MongoHealthCheck extends HealthCheck {
    private MongoClient mongoClient;
    private MongoConfiguration mongoConfiguration;


    public MongoHealthCheck(MongoClient mongoClient, MongoConfiguration mongoConfiguration) {
        this.mongoClient = mongoClient;
        this.mongoConfiguration = mongoConfiguration;
    }

    @Override
    protected Result check() throws Exception {
        MongoDatabase result = this.mongoClient.getDatabase(mongoConfiguration.getDatabase());
        if (result != null) {
            return Result.healthy("Thumbs up from Mongo");
        } else {
            return Result.unhealthy("Thumbs down from Mongo");
        }
    }
}
