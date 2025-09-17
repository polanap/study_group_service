package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.study_group_service.models.Color;
import org.example.study_group_service.models.Country;

@Entity
@Table(name = "person")
@Data
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Поле не может быть null
    private String name; // Поле не может быть null, Строка не может быть пустой

    @Enumerated(EnumType.STRING)
    @Column(name = "eye_color", nullable = false) // Поле не может быть null
    private Color eyeColor; // Поле не может быть null

    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color", nullable = false) // Поле не может быть null
    private Color hairColor; // Поле не может быть null

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private LocationEntity location; // Поле может быть null

    private Double height; // Поле может быть null, Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    private Country nationality; // Поле может быть null
}