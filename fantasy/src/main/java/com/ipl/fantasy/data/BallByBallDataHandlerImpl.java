package com.ipl.fantasy.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ipl.fantasy.dto.MatchData;
import com.ipl.fantasy.persistence.entities.Delivery;
import com.ipl.fantasy.persistence.entities.Innings;
import com.ipl.fantasy.persistence.entities.MatchInfo;
import com.ipl.fantasy.persistence.entities.MetaData;
import com.ipl.fantasy.persistence.mappers.DataMapper;
import com.ipl.fantasy.persistence.repos.DeliveryRepository;
import com.ipl.fantasy.persistence.repos.InningsRepository;
import com.ipl.fantasy.persistence.repos.MatchInfoRepository;
import com.ipl.fantasy.persistence.repos.MetaDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * TODO- setup mySQL
 * TODO- parse YAML
 * TODO- create schemas
 * TODO-
 *
 */
@Slf4j
@Component
public class BallByBallDataHandlerImpl implements BallByBallDataHandler{

    @Autowired
    MetaDataRepository metaDataRepository;

    @Autowired
    MatchInfoRepository matchInfoRepository;

    @Autowired
    InningsRepository inningsRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    DataMapper dataMapper;

    @Override
    public void importMatchYaml(String filePath, String fileName) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            JsonNode node = mapper.readTree(new File(filePath + fileName));
            MatchData matchData = new MatchData(node);
            MetaData metaData = dataMapper.mapMetaDataDtoToEntity(matchData.getMeta());
            metaDataRepository.save(metaData);
            System.out.println("hellpo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void demo(String matchId) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            JsonNode node = mapper.readTree(new File("/Users/tekion/dev/learning/ipl (3)/" + matchId + ".yaml"));
            MatchData matchData = new MatchData(node);
            MetaData metaData = metaDataRepository.save(dataMapper.mapMetaDataDtoToEntity(matchData.getMeta()));
            MatchInfo matchInfo = matchInfoRepository.save(dataMapper.mapMatchInfoDtoToEntity(matchData.getInfo()));
            List<Delivery> deliveries = new ArrayList<>();
            matchData.getInnings().forEach(inningsDto -> {
                Innings innings = dataMapper.mapInningsDtoToEntity(matchData, inningsDto);
                innings.setMatchId(matchInfo.getId());
                Innings savedInnings = inningsRepository.save(innings);
                List<Delivery> deliveries1 = inningsDto.getDeliveries()
                        .stream()
                        .map(dataMapper::mapDeliveryDtoToEntity)
                        .map(delivery -> {
                            delivery.setMatchId(matchInfo.getId());
                            delivery.setInningsId(savedInnings.getId());
                            return delivery;
                        }).collect(Collectors.toList());
                deliveries.addAll(deliveries1);
            });
            deliveryRepository.saveAll(deliveries);
            System.out.println("hellpo");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void importDataForFile(String fileName) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            JsonNode node = mapper.readTree(new File("/Users/tekion/dev/learning/ipl/" + fileName + ".yaml"));
            MatchData matchData = new MatchData(node);
            MetaData metaData = metaDataRepository.save(dataMapper.mapMetaDataDtoToEntity(matchData.getMeta()));
            MatchInfo matchInfo = matchInfoRepository.save(dataMapper.mapMatchInfoDtoToEntity(matchData.getInfo()));
            List<Delivery> deliveries = new ArrayList<>();
            matchData.getInnings().forEach(inningsDto -> {
                Innings innings = dataMapper.mapInningsDtoToEntity(matchData, inningsDto);
                dataMapper.setMatchDetailsInInnings(innings, matchInfo);
                Innings savedInnings = inningsRepository.save(innings);
                AtomicInteger seqNo = new AtomicInteger(1);
                List<Delivery> deliveries1 = inningsDto.getDeliveries()
                        .stream()
                        .map(dataMapper::mapDeliveryDtoToEntity)
                        .map(delivery -> {
                            dataMapper.setInningsDetailsInDelivery(delivery, savedInnings);
                            dataMapper.setMatchDetailsInDelivery(delivery, matchInfo);
                            delivery.setSequenceNumber(seqNo.getAndIncrement());
                            return delivery;
                        }).collect(Collectors.toList());
                deliveries.addAll(deliveries1);
            });
            deliveryRepository.saveAll(deliveries);
            System.out.println("import successfull for " + fileName);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bulkImportByFileNames(List<String> fileNames) {
        fileNames.forEach(this::importDataForFile);
    }



}
