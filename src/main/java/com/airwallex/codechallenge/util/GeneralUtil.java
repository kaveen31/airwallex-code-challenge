package com.airwallex.codechallenge.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeneralUtil {

    private final static ObjectMapper mapper = new ObjectMapper();

    static{
        mapper.registerModule(new JavaTimeModule());
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException("Places is less than zero.");
        }

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public static double percentageDifference(double oldValue, double newValue){
        if(oldValue == 0){
            return 0;
        }
        return 100 * ((newValue - oldValue) / oldValue);
    }

    public static ObjectMapper getObjectMapper(){
        return mapper;
    }
}
