package org.example.db.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class StudyGroup {
    @Id
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studyGroup", cascade = CascadeType.ALL)
    private List<Student> students;

}
