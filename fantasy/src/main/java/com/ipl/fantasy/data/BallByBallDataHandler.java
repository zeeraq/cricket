package com.ipl.fantasy.data;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BallByBallDataHandler {

    void importMatchYaml(String filePath, String fileName);
    void demo(String matchId);
    void bulkImportByFileNames(List<String> fileNames);
}
