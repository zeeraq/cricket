package com.ipl.fantasy.persistence.entities;

import com.sun.istack.Nullable;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Delivery {

    @Id
    @GeneratedValue
    private UUID id;
    private String number;
    private Integer sequenceNumber;
    private String batsman;
    private String bowler;
    private String nonStriker;
    @Nullable
    private String extraType;
    private Integer batsmanRuns;
    private Integer extraRuns;
    private Integer totalRuns;
    private Integer wicket; //1 if wicket falls
    private String playerOut;
    private String wicketKind;
    private String fielders;
    private Integer over;
    private Integer ballNumber;
    //match info
    private UUID matchId;
    private Date date;
    private Integer year;
    private String city;
    private String venue;
    //innings info
    private UUID inningsId;
    private String battingTeam;
    private String bowlingTeam;
    private String inningsName;
    private Integer inningsNumber;
    //populate later
    private Boolean isLegitimate;
}
