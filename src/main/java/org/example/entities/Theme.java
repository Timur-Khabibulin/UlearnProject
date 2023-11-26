package org.example.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
}
