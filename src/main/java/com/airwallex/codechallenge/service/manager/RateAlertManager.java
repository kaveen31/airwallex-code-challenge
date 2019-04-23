package com.airwallex.codechallenge.service.manager;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.model.alert.Alert;
import com.airwallex.codechallenge.service.format.AlertFormatter;
import com.airwallex.codechallenge.service.notification.AlertNotification;
import com.airwallex.codechallenge.service.rule.Rule;

import java.util.List;

public class RateAlertManager implements AlertManager<CurrencyConversionRate> {

    private List<Rule<CurrencyConversionRate, Alert>> currencyConversionRateRules;
    private AlertFormatter<?> formatter;
    private AlertNotification<String> notification;

    public RateAlertManager(List<Rule<CurrencyConversionRate, Alert>> currencyConversionRateRules,
                            AlertFormatter<?> formatter,
                            AlertNotification<String> notification) {

        this.currencyConversionRateRules = currencyConversionRateRules;
        this.formatter = formatter;
        this.notification = notification;
    }

    public void manage(CurrencyConversionRate rate ){
        currencyConversionRateRules.parallelStream().forEach( rule -> {
            Alert alert = rule.process(rate);

            if(alert != null){
                notification.notify(formatter.toString(alert));
            }
        });
    }
}
