package org.example.study_group_service.models.dto.incomming;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Location {
    @NotNull
    private Double x; //Поле не может быть null
    private long y;
    @NotNull
    private Double z; //Поле не может быть null
}
