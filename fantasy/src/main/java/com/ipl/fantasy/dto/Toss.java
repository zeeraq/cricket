package com.ipl.fantasy.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.ipl.fantasy.util.ParseUtil;
import lombok.Data;

@Data
public class Toss {
    private String decision;
    private String winner;

    public Toss(JsonNode node) {
        decision = ParseUtil.getStringValue(node, "decision");
        winner = ParseUtil.getStringValue(node, "winner");
    }
}
