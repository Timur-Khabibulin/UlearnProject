package org.example.charts;

import org.example.db.entities.Mark;
import org.example.db.entities.Student;
import org.example.db.repositories.MarksRepository;
import org.example.db.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChartService implements Runnable {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MarksRepository marksRepository;


    @Override
    public void run() {
        System.out.println("Start drawing charts");
        drawCharts();
        System.out.println("End");
    }

    private void drawCharts() {
        drawCitiesCharts();
        drawThemesScoreChart();
        drawSexChart();
        drawSchoolsChart();
        drawTotalScoresChart();
    }

    private void drawCitiesCharts() {
        var students = studentRepository.findAll();

        var cities = StreamSupport.stream(students.spliterator(), false)
                .filter(p -> p.getCity() != null)
                .collect(Collectors.groupingBy(Student::getCity, Collectors.counting()));

        ChartHelper.createCitiesCharts(cities).forEach(Chart::run);
    }

    private void drawThemesScoreChart() {
        var marks = marksRepository.findAll();
        var studentsCount = studentRepository.count();

        var data = StreamSupport.stream(marks.spliterator(), false)
                .collect(Collectors.groupingBy(it -> it.getExercise().getTheme()))
                .entrySet().stream()
                .map(themeMarks -> {
                    var average = themeMarks.getValue().stream()
                            .mapToDouble(Mark::getScore)
                            .sum() / studentsCount;
                    return new HashMap.SimpleEntry<>(themeMarks.getKey().getName(), (long) average);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        ChartHelper.createSimpleChart(
                data,
                "Средний бал по темам",
                "темы",
                "балл").run();
    }

    private void drawSexChart() {
        var students = studentRepository.findAll();

        var sex = StreamSupport.stream(students.spliterator(), false)
                .filter(p -> p.getSex() != null)
                .collect(Collectors.groupingBy(x -> x.getSex().name(), Collectors.counting()));

        ChartHelper.createSimpleChart(sex, "Пол студентов", "пол", "кол-во").run();
    }

    private void drawSchoolsChart() {
        var students = studentRepository.findAll();

        var schools = StreamSupport.stream(students.spliterator(), false)
                .filter(p -> p.getSchool() != null)
                .collect(Collectors.groupingBy(Student::getSchool, Collectors.counting()));

        ChartHelper.createSimpleChart(schools, "Школы", "школа", "кол-во студентов").run();
    }

    private void drawTotalScoresChart() {
        var students = studentRepository.findAll();

        var data = StreamSupport.stream(students.spliterator(), false)
                .map(student -> {
                    var sum = student.getMarks().stream().map(Mark::getScore).mapToInt(i -> i).sum();
                    return new HashMap.SimpleEntry<>(student, sum);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        ChartHelper.createStudentsTotalScoreChart(data);
    }
}
