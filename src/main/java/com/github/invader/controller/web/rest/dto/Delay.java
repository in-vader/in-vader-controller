package com.github.invader.controller.web.rest.dto;

public class Delay {

    private int min;
    private int max;

    public Delay() {

    }

    public Delay(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}