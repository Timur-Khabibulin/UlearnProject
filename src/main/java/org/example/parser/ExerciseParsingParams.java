package org.example.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.db.entities.Exercise;

@AllArgsConstructor
@Getter
public class ExerciseParsingParams {
    private Exercise exercise;
    private int columnIndex;
}
