package com.airwallex.codechallenge.model.alert;

import java.time.Instant;

public class FallingAlert extends TrendAlert {

    private static final String ALERT = AlertType.SPOT_RATE_FALLING.getDescription();

    public FallingAlert(Instant timeStamp, String currencyPair, long seconds) {
        super(timeStamp, ALERT, currencyPair, seconds);
    }
}
