package com.airwallex.codechallenge.model.alert;

import java.time.Instant;

public class RateAlert extends Alert {

    private String currencyPair;

    public RateAlert(Instant timeStamp, String alert, String currencyPair) {
        super(timeStamp, alert);
        this.currencyPair = currencyPair;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    @Override
    public String toString() {
        return "RateAlert{" +
                "currencyPair='" + currencyPair + '\'' +
                "} " + super.toString();
    }
}
