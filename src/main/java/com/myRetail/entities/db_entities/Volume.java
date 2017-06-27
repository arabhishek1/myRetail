package com.myRetail.entities.db_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by abhishek.ar on 24/06/17.
 */
public class Volume {
    private double l;
    private double b;
    private double h;

    public Volume() {
    }

    public Volume(double l, double b, double h) {
        this.l = l;
        this.b = b;
        this.h = h;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    @JsonIgnore
    public boolean isValid() {
        return (l > 0 &&
                b > 0 &&
                h > 0);
    }

    @Override
    @JsonIgnore
    public String toString() {
        return "Volume{" +
                ", l=" + l +
                ", b=" + b +
                ", h=" + h +
                '}';
    }

    @JsonIgnore
    public double calculateVolume() {
        return l * b * h;
    }

    @JsonIgnore
    public boolean isGreaterThan(Volume volume) {
        return volume == null || calculateVolume() > volume.calculateVolume();
    }

    @Override
    @JsonIgnore
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Volume volume = (Volume) o;

        if (Double.compare(volume.l, l) != 0) return false;
        if (Double.compare(volume.b, b) != 0) return false;
        return Double.compare(volume.h, h) == 0;

    }

    @Override
    @JsonIgnore
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(l);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(h);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @JsonIgnore
    public double getVolumetricWeight() {
        return (l * b * h) / 5000;
    }
}
