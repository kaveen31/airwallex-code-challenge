package com.airwallex.codechallenge.service.rule;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.model.alert.Alert;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//Using Sandro Mancuso test method names https://codurance.com/2014/12/13/naming-test-classes-and-methods/
public class SpotChangeRuleTest {

    private static final double RATE = 0.30000;
    private static final String CURRENCY_PAIR = "CNYAUD";

    private static final int PERIOD = 300;

    private SpotChangeRule rule = new SpotChangeRule(PERIOD, 10, 5);

    @Test
    public void rate_goes_up_by_more_than_required_percentage(){
        //Setup test data
        List<CurrencyConversionRate> currencyConversionRateList
                = getCurrencyConversionRates(PERIOD);

        // Here the new rate is more than 10% higher than the X period average rate
        double percentage = 10.5;
        double newRate = RATE * ((percentage/100) + 1);
        currencyConversionRateList.add(new CurrencyConversionRate(Instant.now(), CURRENCY_PAIR, newRate));

        //Process rates
        List<Alert> alerts = new ArrayList<>();
        currencyConversionRateList.forEach(ccr -> {
            Alert alert = rule.process(ccr);

            if(alert != null){
                alerts.add(alert);
            }
        });
        assertThat(alerts.size()).isEqualTo(1);
    }

    @Test
    public void rate_goes_up_by_not_more_than_required_percentage(){
        //Setup test data
        List<CurrencyConversionRate> currencyConversionRateList
                = getCurrencyConversionRates(PERIOD);

        // Here the new rate is less than 10% higher than the X period average rate
        double percentage = 9;
        double newRate = RATE * ((percentage/100) + 1);
        currencyConversionRateList.add(new CurrencyConversionRate(Instant.now(), CURRENCY_PAIR, newRate));

        //Process rates
        List<Alert> alerts = new ArrayList<>();
        currencyConversionRateList.forEach(ccr -> {
            Alert alert = rule.process(ccr);

            if(alert != null){
                alerts.add(alert);
            }
        });
        assertThat(alerts.size()).isEqualTo(0);
    }

    @Test
    public void rate_goes_down_by_more_than_required_percentage(){
        //Setup test data
        List<CurrencyConversionRate> currencyConversionRateList
                = getCurrencyConversionRates(PERIOD);

        // Here the new rate is less than 10% lower than the x period average rate
        double percentage = 12;
        double newRate = RATE / ((percentage/100) + 1);
        currencyConversionRateList.add(new CurrencyConversionRate(Instant.now(), CURRENCY_PAIR, newRate));

        //Process rates
        List<Alert> alerts = new ArrayList<>();
        currencyConversionRateList.forEach(ccr -> {
            Alert alert = rule.process(ccr);

            if(alert != null){
                alerts.add(alert);
            }
        });
        assertThat(alerts.size()).isEqualTo(1);
    }

    @Test
    public void rate_goes_down_by_not_more_than_required_percentage(){
        //Setup test data
        List<CurrencyConversionRate> currencyConversionRateList
                = getCurrencyConversionRates(PERIOD);

        // Here the new rate is less than 10% lower than the x period average rate
        double percentage = 6;
        double newRate = RATE / ((percentage/100) + 1);
        currencyConversionRateList.add(new CurrencyConversionRate(Instant.now(), CURRENCY_PAIR, newRate));

        //Process rates
        List<Alert> alerts = new ArrayList<>();
        currencyConversionRateList.forEach(ccr -> {
            Alert alert = rule.process(ccr);

            if(alert != null){
                alerts.add(alert);
            }
        });
        assertThat(alerts.size()).isEqualTo(0);
    }


    @Test
    public void average_zero_if_period_not_reached(){
        //Setup test data
        List<CurrencyConversionRate> currencyConversionRateList
                = getCurrencyConversionRates(10);

        // Here the new rate is more than 10% higher than the X period average rate
        double percentage = 10.5;
        double newRate = RATE * ((percentage/100) + 1);
        currencyConversionRateList.add(new CurrencyConversionRate(Instant.now(), CURRENCY_PAIR, newRate));

        //Process rates
        List<Alert> alerts = new ArrayList<>();
        currencyConversionRateList.forEach(ccr -> {
            Alert alert = rule.process(ccr);

            if(alert != null){
                alerts.add(alert);
            }
        });
        assertThat(alerts.size()).isEqualTo(0);
    }

    @Test
    public void process_rates_within_reasonable_time(){
        //Setup test data
        List<CurrencyConversionRate> currencyConversionRateList
                = getCurrencyConversionRates(100000);
        currencyConversionRateList.add(new CurrencyConversionRate(Instant.now(), CURRENCY_PAIR, 0.45000));

        //Process rates
        List<Alert> alerts = new ArrayList<>();
        long start = System.currentTimeMillis();
        currencyConversionRateList.forEach(ccr -> {
            Alert alert = rule.process(ccr);

            if(alert != null){
                alerts.add(alert);
            }
        });
        long end = System.currentTimeMillis();
        assertThat(alerts.size()).isEqualTo(1);

        //Test processing performance within reasonable time
        long elapsedTime = end - start;
        assertThat(elapsedTime).isLessThan(1000);
    }


    /* -------- TEST HELPER METHODS ------------- */
    private List<CurrencyConversionRate> getCurrencyConversionRates(int numberOfRates) {
        List<CurrencyConversionRate> currencyConversionRateList = new ArrayList<>();
        Instant now = Instant.now();
        for(int i=0; i < numberOfRates; i++){
            currencyConversionRateList.add(new CurrencyConversionRate(now, CURRENCY_PAIR, RATE));
            now = now.plus(1,ChronoUnit.SECONDS);
        }
        return currencyConversionRateList;
    }


}
