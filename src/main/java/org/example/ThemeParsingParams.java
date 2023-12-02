package org.example;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.Theme;

@Getter
@Setter
public class ThemeParsingParams {
    private Theme theme;
    private int startIndex;
    private int stopIndex;

    int getTaskStartIndex() {
        return startIndex + 2;
    }

    int getTasksCount() {
        return stopIndex - getTaskStartIndex();
    }
}
