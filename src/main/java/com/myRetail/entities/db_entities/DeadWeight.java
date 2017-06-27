package com.myRetail.entities.db_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by abhishek.ar on 24/06/17.
 */
public class DeadWeight {
    private double weight;

    public DeadWeight() {
    }

    public DeadWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    @JsonIgnore
    public String toString() {
        return "DeadWeight{" +
                "weight=" + weight +
                '}';
    }

    @JsonIgnore
    public boolean isValid() {
        return weight > 0;
    }

    @JsonIgnore
    public boolean isGreaterThan(DeadWeight deadWeight) {
        return deadWeight == null || weight > deadWeight.getWeight();
    }

    @Override
    @JsonIgnore
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeadWeight that = (DeadWeight) o;

        return Double.compare(that.weight, weight) == 0;

    }

    @Override
    @JsonIgnore
    public int hashCode() {
        long temp = Double.doubleToLongBits(weight);
        return (int) (temp ^ (temp >>> 32));
    }
}

