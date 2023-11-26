package org.example.repositories;


import org.example.entities.StudyGroup;
import org.springframework.data.repository.CrudRepository;

public interface StudyGroupRepository extends CrudRepository<StudyGroup, String> {
}
