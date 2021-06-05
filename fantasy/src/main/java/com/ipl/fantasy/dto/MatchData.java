package com.ipl.fantasy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchData {

    //metadata
    private MetaDataDto meta;
    private MatchInfoDto info;
    private List<InningsDto> innings = new ArrayList<>();

    public MatchData(JsonNode node) {
        meta = new MetaDataDto(node.get("meta"));
        info = new MatchInfoDto(node.get("info"));

        node.get("innings").forEach(inningNode -> {
            InningsDto inning = new InningsDto(inningNode);
            innings.add(inning);
        });
    }
}
