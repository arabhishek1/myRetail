package com.myRetail.entities.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myRetail.entities.response.PriceResponse;

/**
 * Created by abhishek.ar on 16/06/17.
 */
public class UpdatePriceRequest {

    private int id;

    @JsonProperty("new_price")
    private PriceResponse price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PriceResponse getPrice() {
        return price;
    }

    public void setPrice(PriceResponse price) {
        this.price = price;
    }
}
