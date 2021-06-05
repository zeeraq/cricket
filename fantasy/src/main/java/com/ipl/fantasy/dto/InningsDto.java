package com.ipl.fantasy.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.ipl.fantasy.util.ParseUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InningsDto {
    private String team;
    private String name;
    private List<DeliveryDto> deliveries = new ArrayList<>();

    public InningsDto(JsonNode node) {
        if (node.fields().hasNext()) {
            name = node.fields().next().getKey();
            JsonNode inningsNode = node.fields().next().getValue();
            team = ParseUtil.getStringValue(inningsNode, "team");
            inningsNode.get("deliveries").forEach(deliveryNode -> {
                DeliveryDto deliveryDto = new DeliveryDto(deliveryNode);
                deliveries.add(deliveryDto);
            });
        }
    }
}
