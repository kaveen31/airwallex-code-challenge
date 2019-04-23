package com.airwallex.codechallenge.model.alert;

import java.time.Instant;

public class SpotChangeAlert extends RateAlert {

    private static final String ALERT = AlertType.SPOT_RATE_CHANGE.getDescription();

    public SpotChangeAlert(Instant timeStamp, String currencyPair) {
        super(timeStamp, ALERT, currencyPair);
    }
}
