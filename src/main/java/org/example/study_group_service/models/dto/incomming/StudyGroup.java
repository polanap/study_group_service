package org.example.study_group_service.models.dto.incomming;

import lombok.Data;
import org.example.study_group_service.models.FormOfEducation;
import org.example.study_group_service.models.Semester;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
public class StudyGroup {
//    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    private Coordinates coordinates; //Поле не может быть null
    @NotNull
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Positive
    private Integer studentsCount; //Значение поля должно быть больше 0, Поле может быть null
    @NotNull
    @Positive
    private Integer expelledStudents; //Значение поля должно быть больше 0, Поле не может быть null
    @NotNull
    @Positive
    private Long transferredStudents; //Значение поля должно быть больше 0, Поле не может быть null
    private FormOfEducation formOfEducation; //Поле может быть null
    @Positive
    private long shouldBeExpelled; //Значение поля должно быть больше 0
    @Positive
    private float averageMark; //Значение поля должно быть больше 0
    private Semester semesterEnum; //Поле может быть null
    @NotNull
    private Person groupAdmin; //Поле не может быть null
}
