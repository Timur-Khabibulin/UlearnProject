package org.example.db.repositories;

import org.example.db.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, String> {
    List<Student> findBySurnameAndName(String surname, String name);
}

