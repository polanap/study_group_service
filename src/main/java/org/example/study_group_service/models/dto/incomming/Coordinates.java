package org.example.study_group_service.models.dto.incomming;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class Coordinates {
    @Min(value = -909, message = "X must be greater than or equal to -909.") // Значение поля должно быть больше -909 (необходимо реализовать свою аннотацию)
    private long x;

    @NotNull(message = "Y cannot be null.") // Поле не может быть null
    @Max(value = 708, message = "Y must be less than or equal to 708.") // Максимальное значение поля: 708
    private Integer y; // Поле не может быть null
}