package com.github.invader.controller.web.rest.dto;

public class Failure {

    private double probability;

    public Failure() {

    }

    public Failure(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }
}