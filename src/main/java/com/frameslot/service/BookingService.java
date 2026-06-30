package com.frameslot.service;

import com.frameslot.domain.Booking;
import com.frameslot.domain.BookingStatus;
import com.frameslot.domain.NotificationType;
import com.frameslot.domain.Role;
import com.frameslot.domain.SessionType;
import com.frameslot.domain.Studio;
import com.frameslot.domain.User;
import com.frameslot.repository.BlockedDateRepository;
import com.frameslot.repository.BookingRepository;
import com.frameslot.web.ApiException;
import com.frameslot.web.dto.BookingDtos.CreateBookingRequest;
import com.frameslot.web.dto.StudioDtos.BookingResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final CurrentUserService currentUserService;
    private final StudioService studioService;
    private final BookingRepository bookingRepository;
    private final BlockedDateRepository blockedDateRepository;
    private final NotificationService notificationService;

    public BookingService(CurrentUserService currentUserService, StudioService studioService,
                          BookingRepository bookingRepository,
                          BlockedDateRepository blockedDateRepository, NotificationService notificationService) {
        this.currentUserService = currentUserService;
        this.studioService = studioService;
        this.bookingRepository = bookingRepository;
        this.blockedDateRepository = blockedDateRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public BookingResponse createBooking(Long customerId, CreateBookingRequest request) {
        User customer = currentUserService.requireUser(customerId, Role.CUSTOMER);
        Studio studio = studioService.requireActiveStudio(request.studioId());
        SessionType sessionType = studioService.requireSessionTypeForStudio(studio, request.sessionTypeId());
        LocalTime endTime = request.startTime().plusHours(sessionType.getDurationHours());
        validateBookableSlot(studio, sessionType, request.bookingDate(), request.startTime(), endTime);

        Booking booking = bookingRepository.save(new Booking(
                customer,
                sessionType,
                studio,
                request.bookingDate(),
                request.startTime(),
                endTime,
                request.eventDetails()
        ));
        notificationService.recordBookingEmail(booking, NotificationType.BOOKING_CREATED);
        return StudioMapper.toBookingResponse(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> myBookings(Long customerId) {
        User customer = currentUserService.requireUser(customerId, Role.CUSTOMER);
        return bookingRepository.findByCustomerOrderByBookingDateDescStartTimeDesc(customer).stream()
                .map(StudioMapper::toBookingResponse)
                .toList();
    }

    @Transactional
    public BookingResponse cancelOwnBooking(Long customerId, Long bookingId, String reason) {
        User customer = currentUserService.requireUser(customerId, Role.CUSTOMER);
        Booking booking = requireBooking(bookingId);
        if (!booking.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Booking does not belong to this customer");
        }
        booking.cancel(defaultReason(reason, "Cancelled by customer"));
        notificationService.recordBookingEmail(booking, NotificationType.BOOKING_CANCELLED);
        return StudioMapper.toBookingResponse(booking);
    }

    @Transactional
    public BookingResponse confirmOwnerBooking(Long ownerId, Long bookingId) {
        Studio studio = studioService.requireOwnerStudio(ownerId);
        Booking booking = requireBookingForStudio(studio, bookingId);
        booking.confirm();
        notificationService.recordBookingEmail(booking, NotificationType.BOOKING_CONFIRMED);
        return StudioMapper.toBookingResponse(booking);
    }

    @Transactional
    public BookingResponse cancelOwnerBooking(Long ownerId, Long bookingId, String reason) {
        Studio studio = studioService.requireOwnerStudio(ownerId);
        Booking booking = requireBookingForStudio(studio, bookingId);
        booking.cancel(defaultReason(reason, "Cancelled by studio"));
        notificationService.recordBookingEmail(booking, NotificationType.BOOKING_CANCELLED);
        return StudioMapper.toBookingResponse(booking);
    }

    private void validateBookableSlot(Studio studio, SessionType sessionType, LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (date.isAfter(LocalDate.now().plusDays(sessionType.getMaxAdvanceDays()))) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Date is outside the max advance booking window");
        }
        if (blockedDateRepository.existsByStudioAndDate(studio, date)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Studio is blocked on this date");
        }
        if (!bookingRepository.findOverlappingBookings(
                studio,
                date,
                startTime,
                endTime,
                List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
        ).isEmpty()) {
            throw new ApiException(HttpStatus.CONFLICT, "Slot is already booked");
        }
    }

    private Booking requireBookingForStudio(Studio studio, Long bookingId) {
        Booking booking = requireBooking(bookingId);
        if (!booking.getStudio().getId().equals(studio.getId())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Booking does not belong to this studio");
        }
        return booking;
    }

    private Booking requireBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Booking not found"));
    }

    private String defaultReason(String reason, String fallback) {
        return reason == null || reason.isBlank() ? fallback : reason;
    }
}
