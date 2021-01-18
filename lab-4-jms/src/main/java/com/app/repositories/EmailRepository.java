package com.app.repositories;

import com.app.entities.Email;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<Email, Long> {
}
