package com.myRetail.bundle;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.myRetail.configurations.MongoConfiguration;
import com.myRetail.healthcheck.MongoHealthCheck;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public abstract class MongoBundle<T extends Configuration> implements ConfiguredBundle<T> {
    private static final Logger logger = LoggerFactory.getLogger(MongoConfiguration.class);

    public abstract MongoConfiguration getMongoConfiguration(T configuration);

    private MongoClient mongoClient = null;

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        final MongoConfiguration mongoConfiguration = getMongoConfiguration(configuration);
        logger.info("Connecting To Mongo with configuration " + mongoConfiguration.toString());
        List<ServerAddress> mongoServerAddress = new ArrayList();
        for (String mongoHost : mongoConfiguration.getMongosHosts()) {
            mongoServerAddress.add(new ServerAddress(mongoHost, mongoConfiguration.getPort()));
        }
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
                .build();
        logger.info("Fetching mongo connection point.....");
        mongoClient = new MongoClient(mongoServerAddress, mongoClientOptions);
        String connectionPoint = mongoClient.getConnectPoint();
        logger.info("Fetched mongo connection point: " + connectionPoint);
        environment.healthChecks().register("mongo-health-check", new MongoHealthCheck(mongoClient, mongoConfiguration));
        logger.info("Connected To Mongo Successfully.....");
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    public MongoClient getMongoClient() {
        return this.mongoClient;
    }
}
