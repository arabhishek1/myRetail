package com.myRetail.entities.db_entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abhishek.ar on 15/06/17.
 */
public class Price {

    @JsonProperty("_id")
    private int productId;

    private double value;

    @JsonProperty("currency_code")
    private String currencyCode;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
