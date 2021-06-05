package com.ipl.fantasy.persistence.entities;

import com.ipl.fantasy.dto.Outcome;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.UUID;

@Data
@Entity
public class MatchInfo {

    @Id
    @GeneratedValue
    private UUID id;
    private String city;
    private String venue;
    private String competition;
    private String dateString;
    private Date date;
    private String gender;
    private String matchType;
    private String winner;
    private String victoryMode;
    private Integer victoryMargin;
    private Integer overs;
    private String playerOfMatch;
    private String homeTeam;
    private String awayTeam;
    private Integer year;
    private String tossWinner;
    private String tossDecision;
    private String umpires;
    //will populate this later
    private Integer isKnockout;         //1 for knockout/final games, 0 otherwise
    private String firstInningsScore;
    private String secondInningsScore;
    private String thirdInningsScore;
    private String fourthInningsScore;
    private boolean tied;
}
