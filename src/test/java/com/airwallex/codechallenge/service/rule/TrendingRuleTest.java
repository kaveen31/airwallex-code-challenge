package com.airwallex.codechallenge.service.rule;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.model.alert.Alert;
import com.airwallex.codechallenge.model.alert.AlertType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//Using Sandro Mancuso test method names https://codurance.com/2014/12/13/naming-test-classes-and-methods/
public class TrendingRuleTest {

    private static final double RATE = 0.30000;
    private static final String CURRENCY_PAIR = "CNYAUD";

    @Test
    public void rising_and_falling_alerts() throws Exception{
        TrendingRule rule = new TrendingRule(6, 2);

        //Setup test data
        List<CurrencyConversionRate> currencyConversionRateList
                = getCurrencyConversionRates(24);

        //Process rates
        List<Alert> alertsRising = new ArrayList<>();
        List<Alert> alertsFalling = new ArrayList<>();

        currencyConversionRateList.forEach(ccr -> {
            Alert alert = rule.process(ccr);

            if(alert != null){
                String alertMsg = alert.getAlert();
                if(alertMsg.equalsIgnoreCase(AlertType.SPOT_RATE_RISING.getDescription())) {
                    alertsRising.add(alert);
                }else if(alertMsg.equalsIgnoreCase(AlertType.SPOT_RATE_FALLING.getDescription())) {
                    alertsFalling.add(alert);
                }
            }
        });

        assertThat(alertsRising.size()).isEqualTo(6);
        assertThat(alertsFalling.size()).isEqualTo(4);
    }

    /* -------- TEST HELPER METHODS ------------- */
    private List<CurrencyConversionRate> getCurrencyConversionRates(int numberOfRates) {
        List<CurrencyConversionRate> currencyConversionRateList = new ArrayList<>();
        Instant now = Instant.now();
        double rate = RATE;
        for(int i=0; i < numberOfRates; i++){

            if(i > numberOfRates/2){
                rate = rate - 0.1;
            }else{
                rate = rate + 0.1;
            }

            currencyConversionRateList.add(new CurrencyConversionRate(now, CURRENCY_PAIR, rate));
            now = now.plus(1,ChronoUnit.SECONDS);
        }
        return currencyConversionRateList;
    }


}
