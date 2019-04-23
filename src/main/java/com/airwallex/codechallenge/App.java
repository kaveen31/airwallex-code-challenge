package com.airwallex.codechallenge;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.input.Reader;
import com.airwallex.codechallenge.model.alert.Alert;
import com.airwallex.codechallenge.service.format.AlertFormatter;
import com.airwallex.codechallenge.service.format.JsonAlertFormatter;
import com.airwallex.codechallenge.service.manager.RateAlertManager;
import com.airwallex.codechallenge.service.notification.AlertNotification;
import com.airwallex.codechallenge.service.notification.ConsoleNotification;
import com.airwallex.codechallenge.service.rule.Rule;
import com.airwallex.codechallenge.service.rule.SpotChangeRule;
import com.airwallex.codechallenge.service.rule.TrendingRule;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class App {

    private static int spotPeriod,
                       percentDiff,
                       roundTo,
                       trendPeriod,
                       rateLimit;

    public static void main(String[] args) {
        //setup config
        setupConfig();

        //setup alert rules
        List<Rule<CurrencyConversionRate, Alert>> rules = getRules();

        //setup formatter
        AlertFormatter<JsonNode> formatter = new JsonAlertFormatter();

        //setup notification strategy
        AlertNotification<String> notification = new ConsoleNotification();

        //setup rate alert manager
        RateAlertManager rateAlertManager = new RateAlertManager(rules, formatter, notification);

        //read rates given file name as arg
        Reader reader = new Reader();
        String filename = args[0];
        Stream<CurrencyConversionRate> stream = reader.read(filename);
        stream.forEach(rateAlertManager::manage);
    }

    private static List<Rule<CurrencyConversionRate, Alert>> getRules() {
        SpotChangeRule spotChangeRule = new SpotChangeRule(spotPeriod, percentDiff, roundTo);
        TrendingRule trendingRule = new TrendingRule(trendPeriod, rateLimit);

        List<Rule<CurrencyConversionRate, Alert>> rules = new ArrayList<>();
        rules.add(spotChangeRule);
        rules.add(trendingRule);
        return rules;
    }

    private static void setupConfig(){
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            percentDiff = Integer.parseInt(prop.getProperty("alert.spot.percentage"));
            spotPeriod = Integer.parseInt(prop.getProperty("alert.spot.average.time.seconds"));
            trendPeriod = Integer.parseInt(prop.getProperty("alert.trend.time.seconds"));
            rateLimit = Integer.parseInt(prop.getProperty("alert.trend.throttle.time.seconds"));
            roundTo = Integer.parseInt(prop.getProperty("rate.round.places"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
