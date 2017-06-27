package com.myRetail.entities.db_entities;

/**
 * Created by abhishek.ar on 24/06/17.
 */
public enum WeightFormula {
    PRODUCT_WEIGHT_CORRELATION("PRODUCT_WEIGHT_CORRELATION"),
    SIMPLE_AGGREGATE("SIMPLE_AGGREGATE"),
    LARGE_AGGREGATE("LARGE_AGGREGATE"),
    WEIGHTED_AVERAGE("WEIGHTED_AVERAGE"),
    LEARNT_PRODUCT_WEIGHT_FORMULA("LEARNT_PRODUCT_WEIGHT"),
    LEARNT_LISTING_WEIGHT_FORMULA("LEARNT_LISTING_WEIGHT");

    private final String name;

    private WeightFormula(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

