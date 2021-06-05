package com.ipl.fantasy.persistence.repos;

import com.ipl.fantasy.persistence.entities.Innings;
import org.springframework.data.repository.CrudRepository;

public interface InningsRepository extends CrudRepository<Innings, String> {
}
