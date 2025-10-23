package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.study_group_service.models.dto.incomming.Coordinates;

import jakarta.validation.constraints.Max;

@Entity
@Table(name = "coordinates")
@Data
public class CoordinatesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Добавляем ID для сущности

    @Column(nullable = false) // Поле не может быть null
    private long x; // Значение поля должно быть больше -909 (необходимо реализовать свою проверку)

    @Column(nullable = false) // Поле не может быть null
    @Max(value = 708) // Максимальное значение поля: 708
    private Integer y; // Поле не может быть null

    public CoordinatesEntity(Coordinates coordinates) {
        this.x = coordinates.getX();
        this.y = coordinates.getY();
    }

    public CoordinatesEntity() {

    }
}