package com.airwallex.codechallenge.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class TrendLine {

    private final Deque<Double> window = new ArrayDeque<>();
    private final int period;
    private Double rate1 = 0.0;
    private Double rate2 = 0.0;
    private int upCount = 1, downCount = 1;

    public TrendLine(int period) {
        this.period = period;
    }

    public void add(double rate) {
        window.add(rate);

        rate1 = window.peekFirst();
        rate2 = rate;

        if (window.size() > period) {
            window.remove();
        }
    }

    public TrendType getTrend() {
        TrendType trendType = TrendType.UNCHANGED;

        if (window.isEmpty()) {
            return trendType;
        }

        if(rate1 > rate2){
            trendType = TrendType.DOWNWARD;
            upCount = 1;
            downCount++;
        }else if(rate1 < rate2){
            trendType = TrendType.UPWARD;
            downCount = 1;
            upCount++;
        }

        return trendType;
    }

    public int getUpCount() {
        return upCount;
    }

    public int getDownCount() {
        return downCount;
    }
}
