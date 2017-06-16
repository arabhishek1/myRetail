package com.myRetail.entities.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abhishek.ar on 16/06/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetProductAndPriceResponse {

    private int id;

    private String name;

    @JsonProperty("current_price")
    private PriceResponse currentPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriceResponse getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(PriceResponse currentPrice) {
        this.currentPrice = currentPrice;
    }
}
