package org.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    private Controller controller;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .headless(false)
                .run(args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Hello");
        controller.run();
    }
}