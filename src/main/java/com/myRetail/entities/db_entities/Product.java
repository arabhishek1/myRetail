package com.myRetail.entities.db_entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class Product {

    @JsonProperty("_id")
    private int productId;

    @JsonProperty("name")
    private String productName;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
