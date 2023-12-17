package org.example.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "theme_id")
    private Theme theme;
}
