package com.ipl.fantasy.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.ipl.fantasy.util.ParseUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MatchInfoDto {
    private String city;
    private String competition;
    private String gender;
    private String matchType;
    private Outcome outcome;
    private Integer overs;
    private List<String> dates = new ArrayList<>();
    private List<String> playerOfMatch = new ArrayList<>();
    private List<String> teams = new ArrayList<>();
    private Toss toss;
    private List<String> umpires = new ArrayList<>();
    private String venue;

    public MatchInfoDto(JsonNode node) {
        city = ParseUtil.getStringValue(node, "city");
        competition = ParseUtil.getStringValue(node, "competition");
        gender = ParseUtil.getStringValue(node, "gender");
        matchType = ParseUtil.getStringValue(node, "match_type");
        outcome = new Outcome(node.get("outcome"));
        overs = Integer.valueOf(ParseUtil.getStringValue(node, "overs"));
        node.get("dates").forEach(date -> {
            dates.add(ParseUtil.getStringValue(date));
        });
        node.get("player_of_match").forEach(player -> {
            playerOfMatch.add(ParseUtil.getStringValue(player));
        });
        node.get("teams").forEach(team -> {
            teams.add(ParseUtil.getStringValue(team));
        });
        toss = new Toss(node.get("toss"));
        node.get("umpires").forEach(umpire -> {
            umpires.add(ParseUtil.getStringValue(umpire));
        });
        venue = ParseUtil.getStringValue(node, "venue");
    }
}
