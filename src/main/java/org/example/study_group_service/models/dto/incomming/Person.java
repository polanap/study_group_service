package org.example.study_group_service.models.dto.incomming;

import lombok.Data;
import org.example.study_group_service.models.Color;
import org.example.study_group_service.models.Country;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class Person {
    @NotNull(message = "Name cannot be null.") // Поле не может быть null
    @NotBlank(message = "Name cannot be empty.") // Строка не может быть пустой
    private String name;

    @NotNull(message = "Eye color cannot be null.") // Поле не может быть null
    private Color eyeColor; // Поле не может быть null

    @NotNull(message = "Hair color cannot be null.") // Поле не может быть null
    private Color hairColor; // Поле не может быть null

    private Long locationId; // Поле может быть null

    @Positive(message = "Height must be greater than 0.") // Значение поля должно быть больше 0, Поле может быть null
    private Double height;

    private Country nationality; // Поле может быть null
}