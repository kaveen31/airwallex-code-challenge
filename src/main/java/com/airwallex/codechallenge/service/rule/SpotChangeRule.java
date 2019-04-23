package com.airwallex.codechallenge.service.rule;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.model.MovingAverage;
import com.airwallex.codechallenge.model.alert.Alert;
import com.airwallex.codechallenge.model.alert.SpotChangeAlert;
import com.airwallex.codechallenge.util.GeneralUtil;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SpotChangeRule implements Rule<CurrencyConversionRate, Alert> {

    private int period,
                percentDiff,
                roundTo;

    private Map<String, MovingAverage> movingAveragePerPair = new HashMap<>();

    public SpotChangeRule(int period, int percentDiff, int roundTo) {
        this.period = period;
        this.percentDiff = percentDiff;
        this.roundTo = roundTo;
    }

    public SpotChangeAlert process(CurrencyConversionRate conversionRate) {
        String currencyPair = conversionRate.getCurrencyPair();
        MovingAverage movingAverage = getMovingAverage(currencyPair);

        // Note order is important here. Get average rate first before adding the next rate to the sliding window.
        Double averageRate = movingAverage.getAverage();

        double currentRate = conversionRate.getRate();
        movingAverage.add(currentRate);

        double percentageDifference = GeneralUtil.percentageDifference(averageRate, currentRate);
        boolean percentageDiffCondition = percentageDiffCondition(percentageDifference);

        SpotChangeAlert alert = null;
        if(percentageDiffCondition){
            Instant timestamp = conversionRate.getTimestamp();
            alert = generateAlert(timestamp, currencyPair);
        }

        return alert;
    }

    private SpotChangeAlert generateAlert(Instant timestamp, String currencyPair) {
        return new SpotChangeAlert(timestamp, currencyPair);
    }

    private boolean percentageDiffCondition(double percentageDifference) {
        double pd = GeneralUtil.round(percentageDifference, roundTo);
        return pd > percentDiff || pd < -percentDiff;
    }

    private MovingAverage getMovingAverage(String currencyPair) {
        MovingAverage movingAverage = movingAveragePerPair.get(currencyPair);

        if (movingAverage == null) {
            movingAverage = new MovingAverage(period);
            movingAveragePerPair.put(currencyPair, movingAverage);
        }
        return movingAverage;
    }

}
