package com.myRetail.sao;

import com.myRetail.configurations.MyRetailConfiguration;
import com.myRetail.exceptions.NonRetryableException;
import com.myRetail.exceptions.RetryableException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public abstract class AbstractSAO {

    private HttpClient httpClient;

    protected MyRetailConfiguration configuration;

    public AbstractSAO(HttpClient httpClient,
                       MyRetailConfiguration configuration) {
        this.httpClient = httpClient;
        this.configuration = configuration;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public MyRetailConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(MyRetailConfiguration configuration) {
        this.configuration = configuration;
    }

    public abstract Logger getLOGGER();

    protected HttpResponse doGet(String url, Map<String, String> headers) throws NonRetryableException, RetryableException {
        getLOGGER().debug(
                "Making GET call to : " + url + " and custom headers : "
                        + headers);
        HttpResponse responseBody = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            // Adding custom headers to request headers
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    httpGet.addHeader(key, headers.get(key));
                }
            }
            responseBody = call(httpGet);
        } catch (NonRetryableException e) {
            getLOGGER().warn("Exception: ", e);
            throw new NonRetryableException(e.getMessage());
        } catch (IOException e) {
            getLOGGER().warn("Exception: ", e);
            throw new NonRetryableException(e.getMessage());
        } catch (RetryableException e) {
            e.printStackTrace();
            throw new RetryableException(e.getMessage());
        }
        return responseBody;
    }

    protected HttpResponse doPost(String url, String payload,
                            Map<String, String> headers) throws NonRetryableException,
            RetryableException {
        getLOGGER().debug(
                "Making POST call to : " + url + " with payload : " + payload
                        + " and custom headers : " + headers);
        HttpResponse responseBody = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            // Adding custom headers to request headers
            for (String key : headers.keySet()) {
                httpPost.addHeader(key, headers.get(key));
            }
            httpPost.setEntity(new StringEntity(payload));
            responseBody = call(httpPost);
        } catch (NonRetryableException | IOException e) {
            getLOGGER().warn("Exception: ", e);
            throw new NonRetryableException(e.getMessage());
        } catch (RetryableException e) {
            getLOGGER().warn("Exception: ", e);
            throw new RetryableException(e.getMessage());
        }
        return responseBody;
    }
    /*

    protected String doPut(String url, String payload,
                            Map<String, String> headers) throws NonRetryableException,
            RetryableException {
        getLOGGER().debug(
                "Making POST call to : " + url + " with payload : " + payload
                        + " and custom headers : " + headers);
        String responseBody = null;
        try {
            HttpPut httpPut = new HttpPut(url);
            // Adding custom headers to request headers
            for (String key : headers.keySet()) {
                httpPut.addHeader(key, headers.get(key));
            }
            httpPut.setEntity(new StringEntity(payload));
            responseBody = call(httpPut);
        } catch (NonRetryableException | IOException e) {
            getLOGGER().warn("Exception: ", e);
            throw new NonRetryableException(e.getMessage());
        } catch (RetryableException e) {
            getLOGGER().warn("Exception: ", e);
            throw new RetryableException(e.getMessage());
        }
        return responseBody;
    }

*/
    private HttpResponse call(HttpUriRequest httpUriRequest) throws IOException,
            NonRetryableException, RetryableException {
        HttpResponse httpResponse = httpClient.execute(httpUriRequest);

        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if (responseCode != HttpStatus.SC_OK) {
            getLOGGER().error(
                    "Call failed with status code : " + responseCode
                            + " and status message : "
                            + httpResponse.getStatusLine().getReasonPhrase());
            if (responseCode >= 500 && responseCode < 600) {
                throw new RetryableException(
                        httpResponse.getStatusLine().getReasonPhrase());
            } else {
                throw new NonRetryableException(httpResponse.getStatusLine().getReasonPhrase());
            }
        }
        getLOGGER().info("responseCode: " + responseCode);
        return httpResponse;
    }

}
