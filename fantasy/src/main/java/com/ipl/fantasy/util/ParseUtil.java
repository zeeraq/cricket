package com.ipl.fantasy.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.List;

public class ParseUtil {

    public static String getStringValue(JsonNode node, String name) {
        return node.get(name).toString().replace("\"", "");
    }

    public static String getStringValue(JsonNode node) {
        return node.toString().replace("\"", "");
    }

    public static String combineStringsFromListToCSV(List<String> values) {
        return values.toString().replace("[", "").replace("]", "");
    }
}
