package com.airwallex.codechallenge.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

//Using Sandro Mancuso test method names https://codurance.com/2014/12/13/naming-test-classes-and-methods/
public class GeneralUtilTest {

    @Test
    public void round_up_to_nearest() {
        double round = GeneralUtil.round(10.587272, 5);
        assertThat(round).isEqualTo(10.58727);

        round = GeneralUtil.round(10.587275, 5);
        assertThat(round).isEqualTo(10.58728);
    }

    @Test
    public void round_error() {
        double round = 0.0;
        try {
            round = GeneralUtil.round(10.587272, -1);
            fail("Negative places should throw an exception");
        } catch (Exception e){
            assertThat(round).isEqualTo(0.0);
        }
    }

    @Test
    public void percentage_difference_negative_value() {
        double oldValue = 0.3, newValue = 0.27;

        double percentageDifference = GeneralUtil.percentageDifference(oldValue, newValue);
        percentageDifference = GeneralUtil.round(percentageDifference, 5);

        assertThat(percentageDifference).isEqualTo(-10.0);
    }

    //This test is for coverage
    @Test
    public void object_mapper_not_null(){
        ObjectMapper objectMapper = GeneralUtil.getObjectMapper();
        assertThat(objectMapper).isNotNull();
    }
}
