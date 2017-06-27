package com.myRetail.entities.db_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abhishek.ar on 24/06/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weight {
    @JsonProperty("dw")
    private DeadWeight deadWeight;

    @JsonProperty("contour_volume")
    private double contourVolume;

    private Volume volume;

    public Weight() {
        deadWeight = new DeadWeight();
        volume = new Volume();
    }

    public Weight(DeadWeight deadWeight, Volume volume) {
        this.deadWeight = deadWeight;
        this.volume = volume;
    }

    public Weight(DeadWeight deadWeight, Volume volume, double contourVolume) {
        this.deadWeight = deadWeight;
        this.volume = volume;
        this.contourVolume = contourVolume;
    }

    public DeadWeight getDeadWeight() {
        return deadWeight;
    }

    public void setDeadWeight(DeadWeight deadWeight) {
        this.deadWeight = deadWeight;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    @JsonIgnore
    public boolean isValidWeight() {
        return (deadWeight != null && deadWeight.isValid());
    }

    @Override
    public String toString() {
        return "Weight{" +
                "deadWeight=" + deadWeight +
                ", volume=" + volume +
                '}';
    }

    @JsonIgnore
    public boolean isValidVolume() {
        return volume != null && volume.isValid();
    }

    @JsonIgnore
    public boolean isValid() {
        return isValidWeight() && isValidVolume();
    }

    @Override
    @JsonIgnore
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weight weight = (Weight) o;

        if (deadWeight != null ? !deadWeight.equals(weight.deadWeight) : weight.deadWeight != null) return false;
        return !(volume != null ? !volume.equals(weight.volume) : weight.volume != null);

    }

    @Override
    @JsonIgnore
    public int hashCode() {
        int result = deadWeight != null ? deadWeight.hashCode() : 0;
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    public double getVolumetricWeight() {
        if (volume == null) return 0;
        return volume.getVolumetricWeight();
    }

    public double getMaxWeight() {
        return Math.max(deadWeight.getWeight() / 1000, volume.getVolumetricWeight());
    }

    public double getContourVolume() {
        return contourVolume;
    }

    public void setContourVolume(double contourVolume) {
        this.contourVolume = contourVolume;
    }

    @JsonIgnore
    public boolean isValidContourVolume() {
        return this.contourVolume > 0;
    }
}

