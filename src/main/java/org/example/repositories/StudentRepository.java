package org.example.repositories;

import org.example.entities.*;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, String> {
}

