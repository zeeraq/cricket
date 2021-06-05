package com.ipl.fantasy.persistence.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Innings {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID matchId;
    private String battingTeam;
    private String bowlingTeam;
    private String name;
    private Integer inningsNumber;      //can be first second, third or fourth
    private Integer numDeliveries;
    private Integer runs;
    private Integer wickets;
    private String lastDeliveryString;
    private String city;
    private String winnerOfMatch;
    private Integer year;
}
