package com.frameslot.web;

import com.frameslot.domain.User;
import com.frameslot.service.BookingService;
import com.frameslot.service.CurrentUserService;
import com.frameslot.service.StudioService;
import com.frameslot.web.dto.StudioDtos.BlockedDateRequest;
import com.frameslot.web.dto.StudioDtos.CancelRequest;
import com.frameslot.web.dto.StudioDtos.SessionTypeRequest;
import com.frameslot.web.dto.StudioDtos.StudioRequest;
import com.frameslot.web.dto.StudioDtos.WorkingHoursRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner")
public class StudioOwnerController {

    private final StudioService studioService;
    private final BookingService bookingService;
    private final CurrentUserService currentUser;

    public StudioOwnerController(StudioService studioService, BookingService bookingService,
                                  CurrentUserService currentUser) {
        this.studioService = studioService;
        this.bookingService = bookingService;
        this.currentUser = currentUser;
    }

    @PostMapping("/studios")
    public ResponseEntity<?> registerStudio(@Valid @RequestBody StudioRequest req) {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(studioService.registerStudio(user.getId(), req));
    }

    @PutMapping("/studios")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody StudioRequest req) {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(studioService.updateProfile(user.getId(), req));
    }

    @PostMapping("/studios/session-types")
    public ResponseEntity<?> addSessionType(@Valid @RequestBody SessionTypeRequest req) {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(studioService.addSessionType(user.getId(), req));
    }

    @PostMapping("/studios/working-hours")
    public ResponseEntity<?> addWorkingHours(@Valid @RequestBody WorkingHoursRequest req) {
        User user = currentUser.getCurrentUser();
        studioService.addWorkingHours(user.getId(), req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/studios/blocked-dates")
    public ResponseEntity<?> blockDate(@Valid @RequestBody BlockedDateRequest req) {
        User user = currentUser.getCurrentUser();
        studioService.blockDate(user.getId(), req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(studioService.dashboard(user.getId()));
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> ownerBookings() {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(studioService.ownerBookings(user.getId()));
    }

    @PutMapping("/bookings/{id}/confirm")
    public ResponseEntity<?> confirmBooking(@PathVariable Long id) {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(bookingService.confirmOwnerBooking(user.getId(), id));
    }

    @PutMapping("/bookings/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id, @RequestBody(required = false) CancelRequest req) {
        User user = currentUser.getCurrentUser();
        String reason = req != null ? req.reason() : null;
        return ResponseEntity.ok(bookingService.cancelOwnerBooking(user.getId(), id, reason));
    }
}
