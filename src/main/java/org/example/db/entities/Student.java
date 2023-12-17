package org.example.db.entities;

import com.vk.api.sdk.objects.base.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Student {
    @Id
    private String ulearnId;
    private String surname;
    private String name;
    private String patronymic;
    private String email;
    private Sex sex;
    private String country;
    private String city;
    private String school;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student", cascade = CascadeType.ALL)
    private List<Mark> marks;
}