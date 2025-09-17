package org.example.study_group_service.service;

import lombok.val;
import org.example.study_group_service.models.RequestStatus;
import org.example.study_group_service.models.entity.AdminRequestEntity;
import org.example.study_group_service.repository.AdminRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminRequestRepository adminRequestRepository;

    public AdminRequestEntity makeRequest(Long userId) {
        if (userService.isAdmin(userId)) {
            return adminRequestRepository.findAdminRequestEntityByUserId(userId);
        }
        val newRequest = new AdminRequestEntity();
        newRequest.setUserId(userId);
        newRequest.setCreationTime(LocalDateTime.now());
        newRequest.setStatus(RequestStatus.NEW);
        return adminRequestRepository.save(newRequest);
    }

    public Page<AdminRequestEntity> getAllRequestsPaginated(PageRequest pageRequest) {
        return adminRequestRepository.findAll(pageRequest);
    }

    public Page<AdminRequestEntity> getAllRequestsWithStatusPaginated(RequestStatus status, PageRequest pageRequest) {
        return adminRequestRepository.findAllByStatus(status, pageRequest);
    }

    public AdminRequestEntity approveRequest(Long requestId, Long userId) {
        return changeStatusOfRequest(requestId, userId, RequestStatus.APPROVED);
    }

    public AdminRequestEntity rejectRequest(Long requestId, Long userId) {
        return changeStatusOfRequest(requestId, userId, RequestStatus.REJECTED);
    }


    public AdminRequestEntity changeStatusOfRequest(Long requestId, Long userId, RequestStatus status) {
        var request = getRequestById(requestId);
        if (RequestStatus.isNext(request.getStatus(), status)) {
            throw new IllegalArgumentException("Can't change status of request to " + status + ". Current status is " + request.getStatus());
        }
        request.setStatus(status);
        request.setProcessionTime(LocalDateTime.now());
        request.setProcessedUserId(userId);
        return adminRequestRepository.save(request);
    }

    public AdminRequestEntity getRequestById(Long requestId) {
        return adminRequestRepository.findById(requestId).get();
    }
}
