package com.ipl.fantasy.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.ipl.fantasy.util.ParseUtil;
import lombok.Data;

@Data
public class MetaDataDto {

    private String dataVersion;
    private String created;
    private String revision;

    public String getDataVersion() {
        return dataVersion;
    }

    MetaDataDto(JsonNode node) {
        dataVersion = ParseUtil.getStringValue(node, "data_version");
        created = ParseUtil.getStringValue(node, "created");
        revision = ParseUtil.getStringValue(node, "revision");
    }
}
