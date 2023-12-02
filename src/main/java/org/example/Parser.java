package org.example;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import org.example.entities.*;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class Parser {

    private final CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
    private final Charset charset = Charset.forName("Cp1251");
    @Getter
    private final ArrayList<Student> students = new ArrayList<>();
    @Getter
    private final ArrayList<Theme> themes = new ArrayList<>();
    @Getter
    private final ArrayList<Exercise> exercises = new ArrayList<>();

    public void parse(String fileName) {
        clearParsingResults();
        try (var reader = initReader(fileName)) {
            String[] line;
            var count = -1;
            var themesParameters = new ArrayList<ThemeParsingParams>();
            var exercisesParameters = new ArrayList<ExerciseParsingParams>();
            while ((line = reader.readNext()) != null) {
                count++;
                if (count == 0) {
                    themesParameters = parseThemes(line);
                    themesParameters.forEach(x -> themes.add(x.getTheme()));
                }
                if (count == 1) {
                    exercisesParameters = parseExercises(line, themesParameters);
                    exercisesParameters.forEach(x -> exercises.add(x.getExercise()));
                } else {
                    students.add(parseStudent(line, exercisesParameters));
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.printf(e.getMessage());
        }
    }

    private void clearParsingResults() {
        students.clear();
        exercises.clear();
        themes.clear();
    }

    private CSVReader initReader(String fileName) throws IOException {
        return new CSVReaderBuilder(
                new FileReader(fileName, charset))
                .withCSVParser(parser)
                .build();
    }

    private ArrayList<ThemeParsingParams> parseThemes(String[] line) {
        var themesParams = new ArrayList<ThemeParsingParams>();

        for (var i = 0; i < line.length; i++) {
            if (line[i].isEmpty()) {
                if (i == line.length - 1) {
                    themesParams.get(themesParams.size() - 1).setStopIndex(i);
                }
            } else {
                if (!themesParams.isEmpty()) {
                    themesParams.get(themesParams.size() - 1).setStopIndex(i);
                }
                var theme = new ThemeParsingParams();
                theme.setTheme(new Theme(i, line[i]));
                theme.setStartIndex(i);
                themesParams.add(theme);
            }
        }
        return new ArrayList<>(themesParams.stream().skip(1).toList());
    }

    private ArrayList<ExerciseParsingParams> parseExercises(String[] line, ArrayList<ThemeParsingParams> themesParameters) {
        var result = new ArrayList<ExerciseParsingParams>();
        for (var i = 0; i < themesParameters.size(); i++) {
            var themeParameters = themesParameters.get(i);
            for (var taskIndex = themeParameters.getTaskStartIndex(); taskIndex < themeParameters.getStopIndex(); taskIndex++) {
                var tokens = line[taskIndex].split(":");
                if (tokens.length > 1) {
                    if (tokens[0].equals("Упр")) {
                        result.add(new ExerciseParsingParams(
                                new Exercise(
                                        taskIndex,
                                        tokens[1],
                                        ExerciseType.EXERCISE,
                                        themes.get(i)
                                ),
                                taskIndex
                        ));
                    } else if (tokens[0].equals("ДЗ")) {
                        result.add(new ExerciseParsingParams(
                                new Exercise(
                                        taskIndex,
                                        tokens[1],
                                        ExerciseType.HOMEWORK,
                                        themes.get(i)
                                ),
                                taskIndex
                        ));
                    }
                }
            }
        }
        return result;
    }

    private Student parseStudent(String[] line, ArrayList<ExerciseParsingParams> exercisesParams) {
        var student = new Student();
        var fullName = line[0].split(" ");
        var group = new StudyGroup();
        group.setName(line[3]);

        student.setUlearnId(line[1]);
        student.setSurname(fullName[0]);
        student.setName(fullName.length > 1 ? fullName[1] : "");
        student.setPatronymic(fullName.length > 2 ? fullName[2] : "");
        student.setEmail(line[2]);
        student.setStudyGroup(group);
        student.setMarks(parseMarks(line, exercisesParams, student));
        return student;
    }

    private ArrayList<Mark> parseMarks(
            String[] line,
            ArrayList<ExerciseParsingParams> exercisesParams,
            Student student) {
        return exercisesParams.stream().map(params -> {
            var mark = new Mark();
            mark.setStudent(student);
            mark.setExercise(params.getExercise());
            mark.setScore(Integer.parseInt(line[params.getColumnIndex()]));
            return mark;
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
