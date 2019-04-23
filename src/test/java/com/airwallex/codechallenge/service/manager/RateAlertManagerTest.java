package com.airwallex.codechallenge.service.manager;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.model.alert.Alert;
import com.airwallex.codechallenge.service.format.AlertFormatter;
import com.airwallex.codechallenge.service.format.JsonAlertFormatter;
import com.airwallex.codechallenge.service.notification.AlertNotification;
import com.airwallex.codechallenge.service.notification.ConsoleNotification;
import com.airwallex.codechallenge.service.rule.Rule;
import com.airwallex.codechallenge.service.rule.SpotChangeRule;
import com.airwallex.codechallenge.service.rule.TrendingRule;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RateAlertManagerTest {

   private static RateAlertManager rateAlertManager;

    @BeforeAll
    public static void setup(){
        SpotChangeRule spotChangeRule = new SpotChangeRule(300, 10, 5);
        TrendingRule trendingRule = new TrendingRule(900, 60);

        List<Rule<CurrencyConversionRate, Alert>> rules = new ArrayList<>();
        rules.add(spotChangeRule);
        rules.add(trendingRule);

        AlertFormatter<JsonNode> formatter = new JsonAlertFormatter();
        AlertNotification<String> notification = new ConsoleNotification();

        rateAlertManager = new RateAlertManager(rules, formatter, notification);
    }

    @Test
    public void alerts_generated(){
        final List<CurrencyConversionRate> rates = getCurrencyConversionRates(30000);

        long start = System.currentTimeMillis();
        rates.forEach(r -> rateAlertManager.manage(r));
        long end = System.currentTimeMillis();

        System.out.println("Time taken (ms) = " + (end - start));
    }

    /* -------- TEST HELPER METHODS ------------- */
    private List<CurrencyConversionRate> getCurrencyConversionRates(int numberOfRates) {
        List<CurrencyConversionRate> currencyConversionRateList = new ArrayList<>();
        Instant now = Instant.now();
        double rate = 0.34251;

        for(int i=0; i < numberOfRates; i++){
            rate = rate + 1;
            currencyConversionRateList.add(new CurrencyConversionRate(now, "CNYAUD", rate));
            now = now.plus(1, ChronoUnit.SECONDS);
        }
        return currencyConversionRateList;
    }
}
