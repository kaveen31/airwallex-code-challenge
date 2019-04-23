package com.airwallex.codechallenge.model.alert;

import java.time.Instant;

public class RisingAlert extends TrendAlert {

    private static final String ALERT = AlertType.SPOT_RATE_RISING.getDescription();

    public RisingAlert(Instant timeStamp, String currencyPair, long seconds) {
        super(timeStamp, ALERT, currencyPair, seconds);
    }
}
