package com.myRetail.entities.db_entities;

/**
 * Created by abhishek.ar on 24/06/17.
 */
public enum CartmanErrorCode {
    INTERNAL_SERVER_ERROR("Internal server error"),
    PROFILER_EVENT_HANDLER_ERROR("Profiler event handler error"),
    WH_EVENT_HANDLER_ERROR("Warehouse event handler error"),
    REVERSE_HANDLER_ERROR("Reverse Shipment event handler error"),
    WEIGHT_NOT_FOUND("Weight not found"),
    SHIPMENT_NOT_FOUND("Shipment not found"),
    EMPTY_LISTING_IDENTIFIER("FSN OR SELLER ID IS EMPTY"),
    MONGO_ERROR("Erro while processing the document"),
    INVALID_METRIC("Invalid metric conersion"),
    EVENT_HANDLER_NOT_FOUND("Cannot find any suitable event handler"),
    INVALID_REQUEST("Invalid request"),
    VARADHI_ERROR("Error while calling varadhi"),
    DROOLS_ERROR("Error while executing drools"),
    TRACKING_ID_EMPTY("Tracking id is empty"),
    MERCHANT_REFERENCE_ID_EMPTY("Merchant reference id is empty"),
    LENGTH_VARIATION("Length Variation Is High"),
    BREADTH_VARIATION("Breadth Variation Is High"),
    HEIGHT_VARIATION("Height Variation Is High"),
    WEIGHT_VARIATION("Weight Variation Is High"),
    WEIGHT_INVALID("LBHW should be a positive value"),
    INVALID_LEARNING_SOURCE("Invalide learning source"),
    AGREEMENT_MASTER_ERROR("Error in getting agreement data"),
    DOBBY_NOT_FOUND("Dobby not found, we have deployed hounds to get him back"),
    DOBBY_CACHE_REFRESH_ERROR("Dobby refused to refresh its cache. We are going behind it with kane"),
    RULES_METADATA_ERROR("Error in reading rules metadata"),
    ERROR_IN_DECIDING_LARGE_VERTICALS("Error in reading verticals");
    private final String name;

    private CartmanErrorCode(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}

