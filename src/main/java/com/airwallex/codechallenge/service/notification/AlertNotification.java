package com.airwallex.codechallenge.service.notification;

public interface AlertNotification<T> {

    void notify(T message);
}
