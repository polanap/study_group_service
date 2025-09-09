package org.example.study_group_service.models.dto.incomming;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class Coordinates {
    @Max(71)
    private int x; //Максимальное значение поля: 71
    @NotNull
    private Long y; //Поле не может быть null
}
