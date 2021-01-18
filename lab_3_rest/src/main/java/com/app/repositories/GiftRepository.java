package com.app.repositories;

import com.app.entities.Gift;
import org.springframework.data.repository.CrudRepository;

public interface GiftRepository extends CrudRepository<Gift, Long> {
}
