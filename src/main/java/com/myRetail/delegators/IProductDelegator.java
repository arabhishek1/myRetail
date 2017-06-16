package com.myRetail.delegators;

import com.myRetail.entities.request.UpdatePriceRequest;
import com.myRetail.entities.response.GetProductAndPriceResponse;
import com.myRetail.entities.response.ProductResponse;
import com.myRetail.exceptions.EntityNotFoundException;
import com.myRetail.exceptions.MongoException;
import com.myRetail.exceptions.NonRetryableException;
import com.myRetail.exceptions.RetryableException;

import java.io.IOException;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public interface IProductDelegator {

    GetProductAndPriceResponse getProductDetails(int productId) throws EntityNotFoundException, RetryableException, NonRetryableException, IOException, MongoException;

    ProductResponse getProductNameById(int productId) throws EntityNotFoundException, MongoException;

    void updatePrice(UpdatePriceRequest updatePriceRequest) throws NonRetryableException, MongoException;
}
