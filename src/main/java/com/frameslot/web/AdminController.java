package com.frameslot.web;

import com.frameslot.domain.User;
import com.frameslot.service.AdminService;
import com.frameslot.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final CurrentUserService currentUser;

    public AdminController(AdminService adminService, CurrentUserService currentUser) {
        this.adminService = adminService;
        this.currentUser = currentUser;
    }

    @GetMapping("/studios")
    public ResponseEntity<?> studios() {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(adminService.studios(user.getId()));
    }

    @PutMapping("/studios/{id}/approve")
    public ResponseEntity<?> approveStudio(@PathVariable Long id) {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(adminService.approveStudio(user.getId(), id));
    }

    @PutMapping("/studios/{id}/deactivate")
    public ResponseEntity<?> deactivateStudio(@PathVariable Long id) {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(adminService.deactivateStudio(user.getId(), id));
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> bookings() {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(adminService.bookings(user.getId()));
    }
}
