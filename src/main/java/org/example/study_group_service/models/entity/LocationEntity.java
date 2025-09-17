package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "location")
@Data
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Поле не может быть null
    private String name; // Поле не может быть null

    private double x;
    private int y;
}