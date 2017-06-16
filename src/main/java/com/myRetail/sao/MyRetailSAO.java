package com.myRetail.sao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.google.inject.Inject;
import com.myRetail.configurations.MyRetailConfiguration;
import com.myRetail.entities.response.ProductResponse;
import com.myRetail.exceptions.NonRetryableException;
import com.myRetail.exceptions.RetryableException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class MyRetailSAO extends AbstractSAO{

    public static final Logger LOGGER = LoggerFactory.getLogger(MyRetailSAO.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public MyRetailSAO(HttpClient httpClient, MyRetailConfiguration configuration) {
        super(httpClient, configuration);
    }

    @Override
    public Logger getLOGGER() {
        return LOGGER;
    }

    public ProductResponse getProductByProductId(int productId) throws RetryableException, NonRetryableException, IOException {
        try {
            String url = "http://localhost:26666/product_name/" + productId;
            Map<String, String> customHeaders = new HashMap<>();
            HttpResponse response = doGet(url, customHeaders);
            ProductResponse productResponse = objectMapper.readValue(response.getEntity().getContent(), ProductResponse.class);
            return productResponse;
        } catch (RetryableException | NonRetryableException | IOException ex){
            throw ex;
        }
    }

}
