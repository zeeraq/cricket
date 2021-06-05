package com.ipl.fantasy.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.ipl.fantasy.util.ParseUtil;
import lombok.Data;

@Data
public class Extras {
    String type;
    Integer runs;

    public Extras(JsonNode node) {
        type = node.fields().next().getKey();
        runs = Integer.valueOf(String.valueOf(node.fields().next().getValue()));
    }
}
