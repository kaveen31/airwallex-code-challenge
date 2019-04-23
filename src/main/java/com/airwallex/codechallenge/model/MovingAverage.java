package com.airwallex.codechallenge.model;

import java.util.ArrayDeque;
import java.util.Queue;

public class MovingAverage {

    private final Queue<Double> window = new ArrayDeque<>();
    private final int period;
    private Double sum = 0.0;
    private int count = 0;

    public MovingAverage(int period) {
        this.period = period;
    }

    public void add(Double rate) {
        count++;
        sum = sum + rate;
        window.add(rate);
        if (window.size() > period) {
            Double remove = window.remove();
            sum = sum - remove;
        }
    }

    public Double getAverage() {
        if (window.isEmpty()) {
            return 0.0;
        }

        double average = 0.0;

        if(count >= period) {
            average = sum / period;
        }

        return average;
    }
}
