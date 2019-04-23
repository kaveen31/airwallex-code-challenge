package com.airwallex.codechallenge.model.alert;

public enum AlertType {

    SPOT_RATE_CHANGE("SpotChange"),
    SPOT_RATE_FALLING("Falling"),
    SPOT_RATE_RISING("Rising"),
    SPOT_RATE_UNCHANGED("SpotUnchanged");

    private String description;

    AlertType(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
