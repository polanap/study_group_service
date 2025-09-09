package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "study_group")
@Data
public class StudyGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
