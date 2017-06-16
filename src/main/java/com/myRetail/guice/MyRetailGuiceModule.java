package com.myRetail.guice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.myRetail.bundle.HttpClientBundle;
import com.myRetail.bundle.MongoBundle;
import com.myRetail.configurations.MyRetailConfiguration;
import com.myRetail.dao.IPriceDAO;
import com.myRetail.dao.IProductDAO;
import com.myRetail.dao.impl.PriceDAO;
import com.myRetail.dao.impl.ProductDAO;
import com.myRetail.delegators.IProductDelegator;
import com.myRetail.delegators.impl.ProductDelegator;
import org.apache.http.client.HttpClient;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class MyRetailGuiceModule extends AbstractModule {
    private MongoBundle mongoBundle;
    private ObjectMapper mapper;
    private HttpClientBundle httpClientBundle;

    public MyRetailGuiceModule(ObjectMapper mapper, HttpClientBundle<MyRetailConfiguration> httpClientBundle, MongoBundle<MyRetailConfiguration> mongoBundle) {
        this.mapper = mapper;
        this.httpClientBundle = httpClientBundle;
        this.mongoBundle = mongoBundle;
    }

    protected void configure() {
        bind(ObjectMapper.class).toInstance(mapper);
        bind(IProductDelegator.class).to(ProductDelegator.class);
        bind(IPriceDAO.class).to(PriceDAO.class);
        bind(IProductDAO.class).to(ProductDAO.class);

    }

    @Provides
    public MongoClient getMongoClient() {
        return this.mongoBundle.getMongoClient();
    }

    @Provides
    public HttpClient getHttpClient() {
        return httpClientBundle.getHttpClient();
    }


}
