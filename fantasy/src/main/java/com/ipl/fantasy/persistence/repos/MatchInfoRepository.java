package com.ipl.fantasy.persistence.repos;

import com.ipl.fantasy.persistence.entities.MatchInfo;
import org.springframework.data.repository.CrudRepository;

public interface MatchInfoRepository extends CrudRepository<MatchInfo, String> {
}
