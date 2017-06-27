package com.myRetail.entities.db_entities;

/**
 * Created by abhishek.ar on 24/06/17.
 */
public enum WeightSource {
    WH("WH"),
    WH_BOX("WH_BOX"),
    WH_BAG("WH_BAG"),
    DERIVED_WEIGHT("DERIVED_WEIGHT"),
    PROFILER("PROFILER"),
    VENDOR("VENDOR"),
    PICKUP_HUB("PICKUP_HUB"),
    LEARNT_PRODUCT_WEIGHT("LEARNT_PRODUCT_WEIGHT"),
    LEARNT_LISTING_WEIGHT("LEARNT_LISTING_WEIGHT"),
    VERTICAL_WEIGHT("VERTICAL_WEIGHT"),
    ZULU_ATTRIBUTES("ZULU_ATTRIBUTES"),
    SELLER_PACKING_DETAILS("SELLER_PACKING_DETAILS"),
    SELLER("SELLER"),
    FORWARD_SHIPMENT("FORWARD_SHIPMENT"),
    FORWARD_SHIPMENT_PWH("FORWARD_SHIPMENT_PWH"),
    VERIFIED_PACKAGE("VERIFIED_PACKAGE"),
    MPS_PACKAGE("MPS_PACKAGE");

    private final String name;

    private WeightSource(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}


