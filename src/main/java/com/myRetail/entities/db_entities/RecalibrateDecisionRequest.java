package com.myRetail.entities.db_entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by abhishek.ar on 26/06/17.
 */
public class RecalibrateDecisionRequest{
    @JsonProperty("merchant_reference_ids")
    private List<String> merchantReferenceIds;

    public List<String> getMerchantReferenceIds() {
        return merchantReferenceIds;
    }

    public void setMerchantReferenceIds(List<String> merchantReferenceIds) {
        this.merchantReferenceIds = merchantReferenceIds;
    }

    @Override
    public String toString() {
        return "RecalibrateDecisionRequest{" +
                "merchantReferenceIds=" + merchantReferenceIds +
                '}';
    }
}
