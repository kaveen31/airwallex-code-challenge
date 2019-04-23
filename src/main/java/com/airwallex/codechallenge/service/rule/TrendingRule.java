package com.airwallex.codechallenge.service.rule;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.model.TrendLine;
import com.airwallex.codechallenge.model.TrendType;
import com.airwallex.codechallenge.model.alert.Alert;
import com.airwallex.codechallenge.model.alert.FallingAlert;
import com.airwallex.codechallenge.model.alert.RisingAlert;
import com.airwallex.codechallenge.model.alert.TrendAlert;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class TrendingRule implements Rule<CurrencyConversionRate, Alert> {

    private int period;
    private int rateLimit;

    private Map<String, TrendLine>  trendLinePerPair = new HashMap<>();
    private Map<String, Instant>  throttleTimePerPair = new HashMap<>();

    public TrendingRule(int period, int rateLimit) {
        this.period = period;
        this.rateLimit = rateLimit;
    }

    public TrendAlert process(CurrencyConversionRate conversionRate) {
        String currencyPair = conversionRate.getCurrencyPair();
        TrendLine prevTrendLine = getTrendLine(currencyPair);
        TrendType trendType = prevTrendLine.getTrend();

        double currentRate = conversionRate.getRate();
        prevTrendLine.add(currentRate);

        int upCount = prevTrendLine.getUpCount();
        int downCount = prevTrendLine.getDownCount();

        Instant timestamp = conversionRate.getTimestamp();

        TrendAlert trendAlert = generateAlert(timestamp, currencyPair, upCount, downCount, trendType);
        return throttle(trendAlert);
    }

    private TrendAlert generateAlert(Instant timestamp, String currencyPair, int upCount, int downCount, TrendType trendType) {
        if(trendType.equals(TrendType.UPWARD)){
            return new RisingAlert(timestamp, currencyPair, upCount);
        }

        if(trendType.equals(TrendType.DOWNWARD)){
            return new FallingAlert(timestamp, currencyPair, downCount);
        }

        return null;
    }


    private TrendAlert throttle(TrendAlert alert){
        if(alert == null){
            return  null;
        }

        String currencyPair = alert.getCurrencyPair();
        Instant timeStamp = alert.getTimeStamp();

        Instant instant = throttleTimePerPair.get(currencyPair);
        if(instant == null){
            throttleTimePerPair.put(currencyPair, timeStamp);
            return null;
        }else{
            Duration between = Duration.between(instant, timeStamp);
            long seconds = between.getSeconds();

            if(seconds >= rateLimit){
                throttleTimePerPair.put(currencyPair, timeStamp);
                return alert;
            }
        }

        return null;
    }

    private TrendLine getTrendLine(String currencyPair) {
        TrendLine trendLine = trendLinePerPair.get(currencyPair);

        if (trendLine == null) {
            trendLine = new TrendLine(period);
            trendLinePerPair.put(currencyPair, trendLine);
        }
        return trendLine;
    }

}
