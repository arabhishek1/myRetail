package com.myRetail;

import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.myRetail.bundle.HttpClientBundle;
import com.myRetail.bundle.MongoBundle;
import com.myRetail.configurations.MongoConfiguration;
import com.myRetail.configurations.MyRetailConfiguration;
import com.myRetail.guice.MyRetailGuiceModule;
import com.myRetail.resources.MyRetailResources;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class MyRetailApplication extends Application<MyRetailConfiguration>{

    protected static final Logger logger = LoggerFactory.getLogger(MyRetailApplication.class);


    private GuiceBundle<MyRetailConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new MyRetailApplication().run(args);
        logger.info("MyRetailApp is ready to serve");
    }

    public MyRetailApplication() {
        super();
    }

    @Override
    public String getName() {
        return "myRetail";
    }

    @Override
    public void initialize(Bootstrap<MyRetailConfiguration> bootstrap) {
        HttpClientBundle<MyRetailConfiguration> httpClientBundle = new HttpClientBundle<MyRetailConfiguration>() {
            @Override
            public HttpClientConfiguration getHttpConfiguration(MyRetailConfiguration configuration) {
                return configuration.getHttpClientConfiguration();
            }
        };

        MongoBundle<MyRetailConfiguration> mongoBundle = new MongoBundle<MyRetailConfiguration>() {
            @Override
            public MongoConfiguration getMongoConfiguration(MyRetailConfiguration configuration) {
                return configuration.getMongoConfiguration();
            }
        };

        guiceBundle = GuiceBundle.<MyRetailConfiguration>newBuilder()
                .setConfigClass(MyRetailConfiguration.class)
                .addModule(new MyRetailGuiceModule(bootstrap.getObjectMapper(),httpClientBundle, mongoBundle))
                .build(Stage.DEVELOPMENT);

        bootstrap.addBundle(httpClientBundle);
        bootstrap.addBundle(mongoBundle);
        bootstrap.addBundle(guiceBundle);

    }

    @Override
    public void run(MyRetailConfiguration myRetailConfiguration, Environment environment) throws Exception {
        environment.jersey().register(guiceBundle.getInjector().getInstance(MyRetailResources.class));
    }
}
