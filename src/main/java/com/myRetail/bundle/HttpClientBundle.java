package com.myRetail.bundle;

import com.netflix.config.DynamicProperty;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public abstract  class HttpClientBundle<T extends Configuration> implements ConfiguredBundle<T> {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientBundle.class);
    public abstract HttpClientConfiguration getHttpConfiguration(T configuration);
    private HttpClient httpClient;

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        logger.info("Registering HttpClient.....");
        final HttpClientConfiguration httpClientConfiguration = getHttpConfiguration(configuration);
        httpClient = new HttpClientBuilder(environment).using(httpClientConfiguration).build(DynamicProperty.getInstance("app.env").getString());
        logger.info("Registered HttpClient Successfully.....");
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }
}
