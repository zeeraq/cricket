package com.ipl.fantasy;

import com.ipl.fantasy.data.BallByBallDataHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/ipl")
@RestController
public class IPLApi {

    @Autowired
    public BallByBallDataHandler ballByBallDataHandler;

    @GetMapping("/health")
    public ResponseEntity health() {
        return ResponseEntity.ok("up");
    }

    @GetMapping("/demo/{matchId}")
    public ResponseEntity demo(@PathVariable String matchId) {
        ballByBallDataHandler.demo(matchId);
        return ResponseEntity.ok("done");
    }

    @PostMapping("/import/list")
    public ResponseEntity importList(@RequestBody List<String> fileNames) {
        ballByBallDataHandler.bulkImportByFileNames(fileNames);
        return ResponseEntity.ok("Bingo!");
    }
}
