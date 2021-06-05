package com.ipl.fantasy.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.ipl.fantasy.util.ParseUtil;
import com.sun.istack.Nullable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
public class DeliveryDto {
    private String number;
    private String batsman;
    private String bowler;
    private String nonStriker;
    private Extras extras;
    private Run runs;
    @Nullable
    private Wicket wicket;
    @Data
    public class Run {
        private Integer batsman;
        private Integer extras;
        private Integer total;
        public Run(JsonNode node) {
            batsman = Integer.valueOf(ParseUtil.getStringValue(node, "batsman"));
            extras = Integer.valueOf(ParseUtil.getStringValue(node, "extras"));
            total = Integer.valueOf(ParseUtil.getStringValue(node, "total"));
        }
    }

    @Data
    public class Wicket {
        private List<String> fielders = new ArrayList<>();
        private String kind;
        private String playerOut;
        public Wicket(JsonNode node) {
            Optional.ofNullable(node.get("fielders")).ifPresent(jsonNode -> {
                jsonNode.forEach(fielder -> {
                    fielders.add(ParseUtil.getStringValue(fielder));
                });
            });
            kind = ParseUtil.getStringValue(node, "kind");
            playerOut = ParseUtil.getStringValue(node, "player_out");
        }
    }
    public DeliveryDto(JsonNode node) {
        number = node.fields().next().getKey();
        JsonNode deliveryNode = node.fields().next().getValue();
        batsman = ParseUtil.getStringValue(deliveryNode, "batsman");
        bowler = ParseUtil.getStringValue(deliveryNode, "bowler");
        nonStriker = ParseUtil.getStringValue(deliveryNode, "non_striker");
        extras = Objects.nonNull(deliveryNode.get("extras")) ? new Extras(deliveryNode.get("extras")) : null;
        runs = new Run(deliveryNode.get("runs"));
        if (Objects.nonNull(deliveryNode.get("wicket"))) {
            wicket = new Wicket(deliveryNode.get("wicket"));
        }
    }
}
