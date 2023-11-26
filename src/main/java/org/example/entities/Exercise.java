package org.example.entities;

import lombok.Data;

import javax.persistence.*;

/*public class Exercise {
    @Id
    private String name;
    private int themeName;
    private int score;
    private int maxScore;
}*/

@Data
@Entity
public class Exercise  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "theme_id")
    private Theme theme;
}
