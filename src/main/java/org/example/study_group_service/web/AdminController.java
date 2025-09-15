package org.example.study_group_service.web;

import org.example.study_group_service.models.RequestStatus;
import org.example.study_group_service.models.dto.outcomming.PageDTO;
import org.example.study_group_service.models.entity.AdminRequestEntity;
import org.example.study_group_service.models.entity.UserEntity;
import org.example.study_group_service.service.AdminService;
import org.example.study_group_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/request")
    public void makeAdminRequests(@RequestParam int userId) {
        userService.addAdminRole(userId);
    }

    @GetMapping("/request/all")
    public PageDTO<AdminRequestEntity> getAdminRequests(@RequestParam int page, @RequestParam int size) {
        var result = adminService.getAllRequestsPaginated(PageRequest.of(page, size));
        return new PageDTO<>(result);
    }

    @GetMapping("/request")
    public PageDTO<AdminRequestEntity> getAdminRequestsWithStatus(@RequestParam RequestStatus status, @RequestParam int page, @RequestParam int size) {
        var result = adminService.getAllRequestsWithStatusPaginated(status, PageRequest.of(page, size));
        return new PageDTO<>(result);
    }


    @PostMapping()
    public void approveAdminRequest(@RequestParam int requestId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        adminService.approveRequest(requestId, currentUser.getId());
    }

    @DeleteMapping()
    public void rejectAdminRequest(@RequestParam int requestId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        adminService.rejectRequest(requestId, currentUser.getId());
    }

    @PostMapping("/user")
    public void addAdminRules(@RequestParam int userId) {
        userService.addAdminRole(userId);
    }

    @DeleteMapping("/user")
    public void removeAdminRules(@RequestParam int userId) {
        userService.removeAdminRole(userId);
    }
}
