package com.ipl.fantasy.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.ipl.fantasy.util.ParseUtil;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;

@Data
public class Outcome {
    private BY by;
    private Integer margin;
    private String winner;
    private String result;
    private String eliminator;
    public enum BY {
        RUNS("runs"), WICKETS("wickets"), SUPEROVER("superover");
        private String means;
        BY(String means) {
            this.means = means;
        }
        public String getMeans() {
            return this.means;
        }
    }

    public Outcome(JsonNode node) {
        if (Objects.nonNull(node.get("by"))) {
            by = Objects.isNull(node.get("by").get("wickets")) ? BY.RUNS : BY.WICKETS;
            margin = Integer.valueOf(String.valueOf(node.get("by").get(by.means)));
        }
        Optional.ofNullable(node.get("winner")).ifPresent(winnerNode -> this.setWinner(ParseUtil.getStringValue(winnerNode)));
        Optional.ofNullable(node.get("result")).ifPresent(result -> {
            this.setResult(ParseUtil.getStringValue(result));
        });
        Optional.ofNullable(node.get("eliminator")).ifPresent(eliminator -> {
            this.setEliminator(ParseUtil.getStringValue(eliminator));
            this.setWinner(ParseUtil.getStringValue(eliminator));
            this.setBy(BY.SUPEROVER);
        });
    }
}
