package org.example.study_group_service.models.dto.incomming;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class Location {
    @NotNull(message = "Name cannot be null.") // Поле не может быть null
    private String name; // Поле не может быть null

    private double x;
    private int y;
}