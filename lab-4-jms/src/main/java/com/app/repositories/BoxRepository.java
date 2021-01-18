package com.app.repositories;

import com.app.entities.Box;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoxRepository extends CrudRepository<Box, Long> {
    List<Box> findByEmail(String email);
}
