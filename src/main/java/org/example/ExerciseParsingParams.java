package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.entities.Exercise;

@AllArgsConstructor
@Getter
public class ExerciseParsingParams {
    private Exercise exercise;
    private int columnIndex;
}
