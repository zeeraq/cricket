package com.ipl.fantasy.persistence.repos;

import com.ipl.fantasy.persistence.entities.MetaData;
import org.springframework.data.repository.CrudRepository;

public interface MetaDataRepository extends CrudRepository<MetaData, String> {
}
