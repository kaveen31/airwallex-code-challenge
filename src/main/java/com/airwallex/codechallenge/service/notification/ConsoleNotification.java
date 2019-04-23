package com.airwallex.codechallenge.service.notification;

public class ConsoleNotification implements AlertNotification<String> {

    @Override
    public void notify(String alert) {
        System.out.println(alert);
    }
}
