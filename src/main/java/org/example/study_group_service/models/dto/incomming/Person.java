package org.example.study_group_service.models.dto.incomming;

import lombok.Data;
import org.example.study_group_service.models.Color;
import org.example.study_group_service.models.Country;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class Person {
    @NotNull
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    private Color eyeColor; //Поле не может быть null
    @NotNull
    private Color hairColor; //Поле не может быть null
    @NotNull
    private Location location; //Поле не может быть null
    @Positive
    private float weight; //Значение поля должно быть больше 0
    @NotNull
    private Country nationality; //Поле может быть null
}
