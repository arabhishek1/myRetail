package com.myRetail.entities.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abhishek.ar on 16/06/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceResponse {

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

}
