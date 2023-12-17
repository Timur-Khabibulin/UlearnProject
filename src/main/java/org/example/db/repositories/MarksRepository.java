package org.example.db.repositories;

import org.example.db.entities.Mark;
import org.springframework.data.repository.CrudRepository;

public interface MarksRepository extends CrudRepository<Mark, Long> {
}
