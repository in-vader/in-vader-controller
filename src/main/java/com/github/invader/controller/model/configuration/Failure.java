package com.github.invader.controller.model.configuration;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Failure {

    @Column
    private Double probabvility;

    public Failure() {

    }

    public Failure(Double probabvility) {
        this.probabvility = probabvility;
    }

    public Double getProbabvility() {
        return probabvility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Failure failure = (Failure) o;

        return Objects.equals(failure.probabvility, probabvility);

    }

    @Override
    public int hashCode() {
        return Objects.hash(probabvility);
    }
}
