package com.frameslot.web;

import com.frameslot.domain.User;
import com.frameslot.service.BookingService;
import com.frameslot.service.CurrentUserService;
import com.frameslot.service.StudioService;
import com.frameslot.web.dto.BookingDtos.CreateBookingRequest;
import com.frameslot.web.dto.StudioDtos.CancelRequest;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final StudioService studioService;
    private final BookingService bookingService;
    private final CurrentUserService currentUser;

    public CustomerController(StudioService studioService, BookingService bookingService,
                              CurrentUserService currentUser) {
        this.studioService = studioService;
        this.bookingService = bookingService;
        this.currentUser = currentUser;
    }

    @GetMapping("/studios")
    public ResponseEntity<?> activeStudios() {
        return ResponseEntity.ok(studioService.activeStudios());
    }

    @GetMapping("/studios/{id}/sessions")
    public ResponseEntity<?> activeSessions(@PathVariable Long id) {
        return ResponseEntity.ok(studioService.activeSessions(id));
    }

    @GetMapping("/studios/{studioId}/sessions/{sessionTypeId}/slots")
    public ResponseEntity<?> availableSlots(@PathVariable Long studioId,
                                             @PathVariable Long sessionTypeId,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(studioService.availableSlots(studioId, sessionTypeId, date));
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@Valid @RequestBody CreateBookingRequest req) {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(bookingService.createBooking(user.getId(), req));
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> myBookings() {
        User user = currentUser.getCurrentUser();
        return ResponseEntity.ok(bookingService.myBookings(user.getId()));
    }

    @PutMapping("/bookings/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id, @RequestBody(required = false) CancelRequest req) {
        User user = currentUser.getCurrentUser();
        String reason = req != null ? req.reason() : null;
        return ResponseEntity.ok(bookingService.cancelOwnBooking(user.getId(), id, reason));
    }
}
