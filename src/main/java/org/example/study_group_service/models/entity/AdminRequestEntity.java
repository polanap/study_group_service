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
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "processed_user_id")
    private Integer processedUserId;

    @Column(name = "procession_time")
    private LocalDateTime processionTime;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.NEW;
}
