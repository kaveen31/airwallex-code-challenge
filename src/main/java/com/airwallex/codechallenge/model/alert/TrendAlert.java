package com.airwallex.codechallenge.model.alert;

import java.time.Instant;

public class TrendAlert extends RateAlert {

    private long seconds;

    public TrendAlert(Instant timeStamp, String alert, String currencyPair, long seconds) {
        super(timeStamp, alert, currencyPair);
        this.seconds = seconds;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "TrendAlert{" +
                "seconds=" + seconds +
                " ,alert=" + super.getAlert() +
                " ,timestamp=" + super.getTimeStamp() +
                " ,currencyPair=" + super.getCurrencyPair() +
                "} ";
    }
}
