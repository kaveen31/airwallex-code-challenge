package com.airwallex.codechallenge.service.format;

import com.airwallex.codechallenge.model.alert.Alert;
import com.airwallex.codechallenge.model.alert.SpotChangeAlert;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


public class JsonAlertFormatterTest {

    private JsonAlertFormatter jsonAlertFormatter = new JsonAlertFormatter();

    private static Alert alert;
    private static String EXPECTED_JSON;

    @BeforeAll
    public static void setup(){
        alert = new SpotChangeAlert(Instant.MAX, "CNYAUD");
        EXPECTED_JSON = "{\"timeStamp\":31556889864403199.999999999,\"alert\":\"SpotChange\",\"currencyPair\":\"CNYAUD\"}";
    }

    @Test
    public void format_to_json_object(){
        JsonNode jsonNode = jsonAlertFormatter.format(alert);
        String jsonString = jsonNode.toString();
        assertThat(jsonString).isEqualTo(EXPECTED_JSON);
    }

    @Test
    public void format_to_json_string(){
        String jsonString = jsonAlertFormatter.toString(alert);
        assertThat(jsonString).isEqualTo(EXPECTED_JSON);
    }
}
