package com.github.invader.controller.model.configuration;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Delay {

    @Column
    private Integer min;

    @Column
    private Integer max;

    public Delay() {

    }

    public Delay(Integer min, Integer max) {
        this.min = min;
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
}
