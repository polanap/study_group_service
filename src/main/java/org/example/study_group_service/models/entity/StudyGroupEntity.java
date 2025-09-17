package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.study_group_service.models.FormOfEducation;
import org.example.study_group_service.models.Semester;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "study_group")
@Data
public class StudyGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(nullable = false) // Поле не может быть null
    private String name; // Поле не может быть null, Строка не может быть пустой

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private CoordinatesEntity coordinates; // Поле не может быть null

    @Column(name = "creation_date", nullable = false) // Поле не может быть null
    private LocalDate creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Column(name = "students_count")
    private Integer studentsCount; // Значение поля должно быть больше 0, Поле может быть null

    @Column(name = "expelled_students", nullable = false) // Поле не может быть null
    private Integer expelledStudents; // Значение поля должно быть больше 0, Поле не может быть null

    @Positive // Значение поля должно быть больше 0
    @Column(name = "transferred_students")
    private int transferredStudents; // Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    @Column(name = "form_of_education")
    private FormOfEducation formOfEducation; // Поле может быть null

    @Column(name = "should_be_expelled")
    private Long shouldBeExpelled; // Значение поля должно быть больше 0, Поле может быть null

    @Column(name = "average_mark", nullable = false) // Поле не может быть null
    private Float averageMark; // Значение поля должно быть больше 0, Поле не может быть null

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semesterEnum; // Поле может быть null

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_admin_id")
    private PersonEntity groupAdmin; // Поле может быть null
}