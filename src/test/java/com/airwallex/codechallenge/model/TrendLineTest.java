package com.airwallex.codechallenge.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrendLineTest {

    @Test
    public void upward_trend_line(){
        int period = 5;
        TrendLine trendLine = new TrendLine(period);
        TrendType trend = trendLine.getTrend();

        assertThat(trend).isEqualTo(TrendType.UNCHANGED);

        for(int i=1; i <= 10; i++) {
            trendLine.add(i);
        }

        trend = trendLine.getTrend();
        assertThat(trend).isEqualTo(TrendType.UPWARD);
    }

    @Test
    public void downward_trend_line(){
        int period = 5;
        TrendLine trendLine = new TrendLine(period);
        TrendType trend = trendLine.getTrend();

        assertThat(trend).isEqualTo(TrendType.UNCHANGED);

        for(int i=10; i >= 1; i--) {
            trendLine.add(i);
        }

        trend = trendLine.getTrend();
        assertThat(trend).isEqualTo(TrendType.DOWNWARD);
    }

}
