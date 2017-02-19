package com.github.invader.controller.model.configuration;

import java.util.Objects;

public class Delay {

    private Integer min;

    private Integer max;

    public Delay() {

    }

    public Delay(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delay delay = (Delay) o;
        return Objects.equals(delay.min, min) && Objects.equals(delay.max, max);

    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "Delay{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
