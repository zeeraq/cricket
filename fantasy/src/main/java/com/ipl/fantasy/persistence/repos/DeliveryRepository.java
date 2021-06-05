package com.ipl.fantasy.persistence.repos;

import com.ipl.fantasy.persistence.entities.Delivery;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryRepository extends CrudRepository<Delivery, String> {
}
