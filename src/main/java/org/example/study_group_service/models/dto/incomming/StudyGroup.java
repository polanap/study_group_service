package org.example.study_group_service.models.dto.incomming;

import lombok.Data;
import org.example.study_group_service.models.FormOfEducation;
import org.example.study_group_service.models.Semester;

import jakarta.validation.constraints.*;

@Data
public class StudyGroup {
    @NotNull(message = "Name cannot be null.") // Поле не может быть null
    @NotBlank(message = "Name cannot be empty.") // Строка не может быть пустой
    private String name;

    @NotNull(message = "Coordinates cannot be null.") // Поле не может быть null
    private Long coordinatesId;

    @PositiveOrZero(message = "Students count must be 0 or greater.") // Значение поля должно быть больше 0, Поле может быть null
    private Integer studentsCount;

    @NotNull(message = "Expelled students cannot be null.") // Поле не может быть null
    @Positive(message = "Expelled students must be greater than 0.") // Значение поля должно быть больше 0
    private Integer expelledStudents;

    @Positive(message = "Transferred students must be greater than 0.") // Значение поля должно быть больше 0
    private int transferredStudents;

    private FormOfEducation formOfEducation; // Поле может быть null

    @PositiveOrZero(message = "Should be expelled must be 0 or greater.") // Значение поля должно быть больше 0, Поле может быть null
    private Long shouldBeExpelled;

    @NotNull(message = "Average mark cannot be null.") // Поле не может быть null
    @Positive(message = "Average mark must be greater than 0.") // Значение поля должно быть больше 0
    private Float averageMark;

    private Semester semesterEnum; // Поле может быть null

    private Long groupAdminId; // Поле может быть null
}
