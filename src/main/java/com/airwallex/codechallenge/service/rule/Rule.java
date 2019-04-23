package com.airwallex.codechallenge.service.rule;

import com.airwallex.codechallenge.model.alert.Alert;

public interface Rule<T, V extends Alert> {

    V process(T context);
}
