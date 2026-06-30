package com.frameslot.web.dto;

import com.frameslot.domain.BookingStatus;
import com.frameslot.domain.SessionName;
import com.frameslot.domain.StudioStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public final class StudioDtos {

    private StudioDtos() {
    }

    public record StudioRequest(
            @NotBlank String name,
            @NotBlank String location,
            String bio,
            String instagramLink
    ) {
    }

    public record StudioResponse(
            Long id,
            Long ownerId,
            String name,
            String location,
            String bio,
            String instagramLink,
            StudioStatus status
    ) {
    }

    public record SessionTypeRequest(
            @NotNull SessionName name,
            @NotNull @Min(1) Integer durationHours,
            @NotNull @DecimalMin("0.0") BigDecimal price,
            @NotNull @Min(1) Integer maxAdvanceDays
    ) {
    }

    public record SessionTypeResponse(
            Long id,
            Long studioId,
            SessionName name,
            Integer durationHours,
            BigDecimal price,
            Integer maxAdvanceDays,
            boolean active
    ) {
    }

    public record WorkingHoursRequest(
            @NotNull DayOfWeek dayOfWeek,
            @NotNull LocalTime openTime,
            @NotNull LocalTime closeTime
    ) {
    }

    public record BlockedDateRequest(
            @NotNull @FutureOrPresent LocalDate date,
            @NotBlank String reason
    ) {
    }

    public record AvailableSlotsResponse(
            Long studioId,
            Long sessionTypeId,
            LocalDate date,
            List<SlotResponse> slots
    ) {
    }

    public record SlotResponse(LocalTime startTime, LocalTime endTime) {
    }

    public record DashboardResponse(
            long today,
            long thisWeek,
            long thisMonth,
            List<BookingResponse> upcoming
    ) {
    }

    public record BookingResponse(
            Long id,
            Long studioId,
            String studioName,
            Long customerId,
            String customerName,
            String customerEmail,
            String customerPhone,
            Long sessionTypeId,
            SessionName sessionName,
            LocalDate bookingDate,
            LocalTime startTime,
            LocalTime endTime,
            String eventDetails,
            BookingStatus status,
            String cancellationReason
    ) {
    }

    public record CancelRequest(String reason) {
    }
}
