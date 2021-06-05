package com.ipl.fantasy.persistence.mappers;

import com.ipl.fantasy.dto.*;
import com.ipl.fantasy.persistence.entities.Delivery;
import com.ipl.fantasy.persistence.entities.Innings;
import com.ipl.fantasy.persistence.entities.MatchInfo;
import com.ipl.fantasy.persistence.entities.MetaData;
import com.ipl.fantasy.util.DateUtil;
import com.ipl.fantasy.util.ParseUtil;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataMapper {
    public MetaData mapMetaDataDtoToEntity(MetaDataDto metaDataDto) {
        MetaData metaData = new MetaData();
        metaData.setDataVersion(metaDataDto.getDataVersion());
        metaData.setCreated(metaDataDto.getCreated());
        metaData.setRevision(metaDataDto.getRevision());
        return metaData;
    }

    public MatchInfo mapMatchInfoDtoToEntity(MatchInfoDto matchInfoDto) throws ParseException {
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setCity(matchInfoDto.getCity());
        matchInfo.setCompetition(matchInfoDto.getCompetition());
        matchInfo.setGender(matchInfoDto.getGender());
        matchInfo.setMatchType(matchInfoDto.getMatchType());
        matchInfo.setWinner(matchInfoDto.getOutcome().getWinner());
        matchInfo.setVictoryMode(matchInfoDto.getOutcome().getBy().getMeans());
        matchInfo.setVictoryMargin(matchInfoDto.getOutcome().getMargin());
        matchInfo.setOvers(matchInfoDto.getOvers());
        matchInfo.setVenue(matchInfoDto.getVenue());
        matchInfo.setPlayerOfMatch(ParseUtil.combineStringsFromListToCSV(matchInfoDto.getPlayerOfMatch()));
        matchInfo.setHomeTeam(matchInfoDto.getTeams().get(0));
        matchInfo.setAwayTeam(matchInfoDto.getTeams().get(1));
        matchInfo.setDateString(ParseUtil.combineStringsFromListToCSV(matchInfoDto.getDates()));
        String startDate = matchInfoDto.getDates().get(0);
        long timestamp = DateUtil.getTimeStampFromDateString(startDate);
        matchInfo.setDate(new Date(timestamp));
        matchInfo.setYear(DateUtil.getYearFromTimestamp(timestamp));
        matchInfo.setTossWinner(matchInfoDto.getToss().getWinner());
        matchInfo.setTossDecision(matchInfoDto.getToss().getDecision());
        matchInfo.setUmpires(ParseUtil.combineStringsFromListToCSV(matchInfoDto.getUmpires()));
        return matchInfo;
    }

    public Innings mapInningsDtoToEntity(MatchData matchData, InningsDto inningsDto) {
        Innings innings = new Innings();
        innings.setBattingTeam(inningsDto.getTeam());
        String bowlingTeam = matchData.getInfo().getTeams().stream()
                .filter(team -> !team.equals(inningsDto.getTeam()))
                .collect(Collectors.toList()).get(0);
        innings.setBowlingTeam(bowlingTeam);
        innings.setName(inningsDto.getName());
        switch (inningsDto.getName()) {
            case "1st innings" :
                innings.setInningsNumber(1);
                break;
            case "2nd innings" :
                innings.setInningsNumber(2);
                break;
            case "3rd innings" :
                innings.setInningsNumber(3);
                break;
            case "4th innings":
                innings.setInningsNumber(4);
                break;
        }
        List<DeliveryDto> deliveries = inningsDto.getDeliveries();
        innings.setRuns(getTotalRuns(deliveries));
        innings.setWickets(getTotalWickets(deliveries));
        innings.setLastDeliveryString(deliveries.get(deliveries.size() - 1).getNumber());
        return innings;
    }

    private Integer getTotalRuns(List<DeliveryDto> deliveries) {
        Integer totalRuns = deliveries.stream()
                .map(DeliveryDto::getRuns)
                .map(DeliveryDto.Run::getTotal)
                .reduce(0, Integer::sum);
        return totalRuns;
    }

    private Integer getTotalWickets(List<DeliveryDto> deliveries) {
        Integer wickets = (int) deliveries.stream()
                .map(DeliveryDto::getWicket)
                .filter(Objects::nonNull)
                .count();
        return wickets;
    }


    public Delivery mapDeliveryDtoToEntity(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setNumber(deliveryDto.getNumber());
        delivery.setBatsman(deliveryDto.getBatsman());
        delivery.setNonStriker(deliveryDto.getNonStriker());
        delivery.setBowler(deliveryDto.getBowler());
        delivery.setIsLegitimate(true);
        String deliveryString = deliveryDto.getNumber();
        String[] strs = deliveryString.split("\\.");
        delivery.setOver(Integer.parseInt(strs[0]));
        delivery.setBallNumber(Integer.parseInt(strs[1]));
        Optional.ofNullable(deliveryDto.getExtras()).ifPresent(extras -> {
            delivery.setExtraType(extras.getType());
            if (extras.getType().equals("wides") || extras.getType().equals("noballs")) {
                delivery.setIsLegitimate(false);
            }
        });

        delivery.setBatsmanRuns(deliveryDto.getRuns().getBatsman());
        delivery.setExtraRuns(deliveryDto.getRuns().getExtras());
        delivery.setTotalRuns(deliveryDto.getRuns().getTotal());
        Optional.ofNullable(deliveryDto.getWicket()).ifPresent(wicket -> {
            delivery.setWicket(1);
            delivery.setPlayerOut(wicket.getPlayerOut());
            delivery.setWicketKind(wicket.getKind());
            delivery.setFielders(wicket.getFielders().stream().collect(Collectors.joining("-", "{", "}")));
        });
        return delivery;
    }

    public void setInningsDetailsInDelivery(Delivery delivery, Innings innings) {
        delivery.setInningsId(innings.getId());
        delivery.setBattingTeam(innings.getBattingTeam());
        delivery.setBowlingTeam(innings.getBowlingTeam());
        delivery.setInningsName(innings.getName());
        delivery.setInningsNumber(innings.getInningsNumber());
    }

    public void setMatchDetailsInDelivery(Delivery delivery, MatchInfo matchInfo) {
        delivery.setMatchId(matchInfo.getId());
        delivery.setDate(matchInfo.getDate());
        delivery.setYear(matchInfo.getYear());
        delivery.setCity(matchInfo.getCity());
        delivery.setVenue(matchInfo.getVenue());
    }

    public void setMatchDetailsInInnings(Innings innings, MatchInfo matchInfo) {
        innings.setMatchId(matchInfo.getId());
        innings.setCity(matchInfo.getCity());
        innings.setYear(matchInfo.getYear());
        innings.setWinnerOfMatch(matchInfo.getWinner());
    }

}
