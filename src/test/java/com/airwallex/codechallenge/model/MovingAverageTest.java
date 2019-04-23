package com.airwallex.codechallenge.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MovingAverageTest {

    @Test
    public void average_when_adding_rates(){
        int period = 5;
        MovingAverage movingAverage = new MovingAverage(period);
        Double average = movingAverage.getAverage();
        assertThat(average).isEqualTo(0.0);

        for(int i=0; i < period; i++) {
            movingAverage.add(i + 0.1);
        }

        average = movingAverage.getAverage();
        assertThat(average).isEqualTo(2.1);
    }

}
