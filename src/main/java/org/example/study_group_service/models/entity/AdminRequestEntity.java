package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admin_request")
@Data
public class AdminRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
