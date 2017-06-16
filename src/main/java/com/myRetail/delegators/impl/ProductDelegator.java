package com.myRetail.delegators.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.myRetail.dao.IPriceDAO;
import com.myRetail.dao.IProductDAO;
import com.myRetail.delegators.IProductDelegator;
import com.myRetail.entities.db_entities.Price;
import com.myRetail.entities.db_entities.Product;
import com.myRetail.entities.request.UpdatePriceRequest;
import com.myRetail.entities.response.GetProductAndPriceResponse;
import com.myRetail.entities.response.PriceResponse;
import com.myRetail.entities.response.ProductResponse;
import com.myRetail.exceptions.EntityNotFoundException;
import com.myRetail.exceptions.MongoException;
import com.myRetail.exceptions.NonRetryableException;
import com.myRetail.exceptions.RetryableException;
import com.myRetail.sao.MyRetailSAO;

import java.io.IOException;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class ProductDelegator implements IProductDelegator {

    @Inject
    IPriceDAO priceDAO;

    @Inject
    IProductDAO productDAO;

    @Inject
    MyRetailSAO myRetailSAO;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public GetProductAndPriceResponse getProductDetails(int productId) throws EntityNotFoundException, RetryableException, NonRetryableException, IOException, MongoException {
        try {
            //Preprocess, get all the validations and non DB processing calls done
            ProductResponse productResponse = myRetailSAO.getProductByProductId(productId);
            if (productResponse == null)
                throw new EntityNotFoundException("Product Not Found!!! ");

            //Actual processing. Current DB transaction
            Price price = priceDAO.getPriceByProductId(productId);
            if (price == null)
                throw new EntityNotFoundException("Product Price Not Found!!!");

            //Post Process : Once the DB activity is done, wrapper the output as required
            GetProductAndPriceResponse response = new GetProductAndPriceResponse();
            response.setId(productId);
            response.setName(productResponse.getProductName());
            response.setCurrentPrice(objectMapper.convertValue(price, PriceResponse.class));
            return response;
        } catch (RetryableException | NonRetryableException | IOException | MongoException ex){
            throw ex;
        }
    }

    public ProductResponse getProductNameById(int productId) throws EntityNotFoundException, MongoException {
        try {
            Product product = productDAO.getProductByProductId(productId);
            if (product == null)
                throw new EntityNotFoundException("Product Not Found !!! ");
            ProductResponse response = new ProductResponse();
            response.setProductId(product.getProductId());
            response.setProductName(product.getProductName());
            return response;
        } catch (MongoException ex){
            throw ex;
        }
    }

    @Override
    public void updatePrice(UpdatePriceRequest updatePriceRequest) throws NonRetryableException, MongoException {
        validateRequest(updatePriceRequest);
        Price newPrice = getPriceFromRequest(updatePriceRequest);
        priceDAO.updatePrice(newPrice);
    }

    private void validateRequest(UpdatePriceRequest updatePriceRequest) throws NonRetryableException {
        if (updatePriceRequest == null || updatePriceRequest.getPrice() == null )
            throw new NonRetryableException("BadRequest !!!");
    }

    private Price getPriceFromRequest(UpdatePriceRequest updatePriceRequest) {
        Price newPrice = new Price();
        newPrice.setCurrencyCode(updatePriceRequest.getPrice().getCurrencyCode());
        newPrice.setValue(updatePriceRequest.getPrice().getValue());
        newPrice.setProductId(updatePriceRequest.getId());
        return newPrice;
    }
}
