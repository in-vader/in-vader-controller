package com.github.invader.controller.model.configuration;

import java.util.Objects;

public class Failure {

    private Double probability;

    public Failure() {
    }

    public Failure(Double probability) {
        this.probability = probability;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Failure failure = (Failure) o;

        return Objects.equals(failure.probability, probability);

    }

    @Override
    public int hashCode() {
        return Objects.hash(probability);
    }

    @Override
    public String toString() {
        return "Failure{" +
                "probability=" + probability +
                '}';
    }
}
