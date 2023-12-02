package org.example;

import org.example.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private Parser parser;

    @GetMapping
    public void doWork() {
        parser.parse("basicprogramming.csv");
        System.out.println("parsing end");
        studentRepository.saveAll(parser.getStudents());
        System.out.println("saving end");
    }
}
