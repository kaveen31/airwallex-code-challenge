package com.airwallex.codechallenge.service.format;

import com.airwallex.codechallenge.model.alert.Alert;
import com.airwallex.codechallenge.util.GeneralUtil;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonAlertFormatter implements AlertFormatter<JsonNode> {

    @Override
    public JsonNode format(Alert alert) {
        return GeneralUtil.getObjectMapper().convertValue(alert, JsonNode.class);
    }

    @Override
    public String toString(Alert alert) {
        JsonNode jsonNode = format(alert);
        return jsonNode.toString();
    }
}
