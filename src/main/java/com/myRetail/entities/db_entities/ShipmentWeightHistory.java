package com.myRetail.entities.db_entities;

/**
 * Created by abhishek.ar on 24/06/17.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

/**
 * Created by manohark on 07/09/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentWeightHistory {
    @JsonProperty("mrf_id")
    private String merchantRefId;

    @JsonProperty("vt_id")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String trackingId;

    private Weight weight;

    @JsonProperty("source")
    private WeightSource weightSource;

    @JsonProperty("formula")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private WeightFormula weightFormula;

    @JsonProperty("profiler_id")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String profilerId;

    @JsonProperty("weight_source_name")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String weightSourceName;

    @JsonProperty("created_at")
    private DateTime createdAt;

    @JsonProperty("updated_at")
    private DateTime updatedAt;

    @JsonProperty("req_id")
    private String requestId;

    @JsonProperty("_id")
    private String shardKey;

    @JsonProperty("forward_mrf_id")
    private String forwardMrfId;

    @JsonProperty("forward_vt_id")
    private String forwardVtId;

    @JsonProperty("forward_dead_weight_source")
    private WeightSource forwardDeadWeightSource;

    @JsonProperty("forward_volume_source")
    private WeightSource forwardVolumeSource;

    public ShipmentWeightHistory() {
    }

    public ShipmentWeightHistory(String merchantRefId, WeightSource weightSource, Weight weight) {
        this.merchantRefId = merchantRefId;
        this.weightSource = weightSource;
        this.weight = weight;
    }

    public String getMerchantRefId() {
        return merchantRefId;
    }

    public void setMerchantRefId(String merchantRefId) {
        this.merchantRefId = merchantRefId;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public WeightSource getWeightSource() {
        return weightSource;
    }

    public void setWeightSource(WeightSource weightSource) {
        this.weightSource = weightSource;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public WeightFormula getWeightFormula() {
        return weightFormula;
    }

    public void setWeightFormula(WeightFormula weightFormula) {
        this.weightFormula = weightFormula;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getShardKey() {
        return shardKey;
    }

    public void setShardKey(String shardKey) {
        this.shardKey = shardKey;
    }

    public String getWeightSourceName() {
        return weightSourceName;
    }

    public void setWeightSourceName(String weightSourceName) {
        this.weightSourceName = weightSourceName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getForwardMrfId() {
        return forwardMrfId;
    }

    public void setForwardMrfId(String forwardMrfId) {
        this.forwardMrfId = forwardMrfId;
    }

    public String getForwardVtId() {
        return forwardVtId;
    }

    public void setForwardVtId(String forwardVtId) {
        this.forwardVtId = forwardVtId;
    }

    public WeightSource getForwardDeadWeightSource() {
        return forwardDeadWeightSource;
    }

    public void setForwardDeadWeightSource(WeightSource forwardDeadWeightSource) {
        this.forwardDeadWeightSource = forwardDeadWeightSource;
    }

    public WeightSource getForwardVolumeSource() {
        return forwardVolumeSource;
    }

    public void setForwardVolumeSource(WeightSource forwardVolumeSource) {
        this.forwardVolumeSource = forwardVolumeSource;
    }

    public String getProfilerId() {
        return profilerId;
    }

    public void setProfilerId(String profilerId) {
        this.profilerId = profilerId;
    }

    @Override
    public String toString() {
        return "ShipmentWeightHistory{" +
                "merchantRefId='" + merchantRefId + '\'' +
                ", trackingId='" + trackingId + '\'' +
                ", weight=" + weight +
                ", weightSource=" + weightSource +
                ", weightFormula=" + weightFormula +
                ", weightSourceName='" + weightSourceName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", requestId='" + requestId + '\'' +
                ", shardKey='" + shardKey + '\'' +
                ", forwardMrfId='" + forwardMrfId + '\'' +
                ", forwardVtId='" + forwardVtId + '\'' +
                '}';
    }
}

