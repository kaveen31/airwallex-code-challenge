package com.airwallex.codechallenge.model.alert;

import java.time.Instant;

public class Alert {

    private Instant timeStamp;
    private String alert;

    public Alert(Instant timeStamp, String alert) {
        this.timeStamp = timeStamp;
        this.alert = alert;
    }

    public Alert() {
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
}
