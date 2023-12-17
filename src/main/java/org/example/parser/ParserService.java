package org.example.parser;

import org.example.db.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParserService implements Runnable {
    @Autowired
    private Parser parser;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run() {
        System.out.println("Start parsing");
        parser.parse("basicprogramming.csv");
        System.out.println("End parsing");
        System.out.println("Start saving");
        studentRepository.saveAll(parser.getStudents());
        System.out.println("End saving");
    }
}
