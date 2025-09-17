package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.study_group_service.models.RequestStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_request")
@Data
public class AdminRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "processed_user_id")
    private Long processedUserId;

    @Column(name = "procession_time")
    private LocalDateTime processionTime;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.NEW;

    public AdminRequestEntity() {}

    public AdminRequestEntity(Long userId) {
        this.userId = userId;
        this.creationTime = LocalDateTime.now();
        this.status = RequestStatus.NEW;
    }
}
