package com.airwallex.codechallenge.service.format;

import com.airwallex.codechallenge.model.alert.Alert;

public interface AlertFormatter<T> {

    T format(Alert alert);

    String toString(Alert alert);
}
