package com.myRetail.sao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.myRetail.configurations.MyRetailConfiguration;
import com.myRetail.entities.db_entities.RecalibrateDecisionRequest;
import com.myRetail.entities.response.ProductResponse;
import com.myRetail.exceptions.NonRetryableException;
import com.myRetail.exceptions.RetryableException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    public Response recalibrateWeight(List<String> merchantRefIds){
        try {

            List<List<String>> partitions = Lists.partition(merchantRefIds, 500);
            System.out.println(partitions.size());
            for(List<String> partition : partitions) {
                String url = "http://10.85.50.86/rectify_weights/re_run_rules";
                Map<String, String> customHeaders = new HashMap<>();
                customHeaders.put("Access-Control-Allow-Origin", "*");
                customHeaders.put(HTTP.CONTENT_TYPE, "application/json");

                RecalibrateDecisionRequest request = new RecalibrateDecisionRequest();
                request.setMerchantReferenceIds(partition);
                doPost(url, objectMapper.writeValueAsString(request), customHeaders);
            }
            System.out.println("recalibrating of " + merchantRefIds.size() +" shipments complete");
        } catch (NonRetryableException e) {
            e.printStackTrace();
        } catch (RetryableException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }

}
