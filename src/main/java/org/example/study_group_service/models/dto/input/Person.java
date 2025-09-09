package org.example.study_group_service.models.dto.input;

import org.example.study_group_service.models.Color;
import org.example.study_group_service.models.Country;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле не может быть null
    private Location location; //Поле не может быть null
    private float weight; //Значение поля должно быть больше 0
    private Country nationality; //Поле может быть null
}
