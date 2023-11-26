package org.example;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.example.entities.Student;
import org.example.entities.StudyGroup;
import org.example.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class Parser {

    private final CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
    private final Charset charset = Charset.forName("Cp1251");
    @Autowired
    private StudentRepository studentRepository;

    public ArrayList<Student> parse(String fileName) {
        var all = new ArrayList<Student>();
        try (var reader = initReader(fileName)) {

            String[] line;
            var count = -1;
            while ((line = reader.readNext()) != null) {
                count++;

                if (count == 0) {
                    for (var i = 0; i < line.length; i++) {

                    }
                }

                if (count < 3) continue;
                var fullName = line[0].split(" ");
                var group = new StudyGroup();
                group.setName(line[3]);
                all.add(new Student(
                                line[1],
                                fullName[0],
                                fullName.length > 1 ? fullName[1] : "",
                                fullName.length > 2 ? fullName[2] : "",
                                line[2],
                                group,
                                new ArrayList<>() {
                                }
                        )
                );
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return all;
    }

    private CSVReader initReader(String fileName) throws IOException {
        return new CSVReaderBuilder(
                new FileReader(fileName, charset))
                .withCSVParser(parser)
                .build();
    }
}
