package com.frameslot.web.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public final class BookingDtos {

    private BookingDtos() {
    }

    public record CreateBookingRequest(
            @NotNull Long studioId,
            @NotNull Long sessionTypeId,
            @NotNull @FutureOrPresent LocalDate bookingDate,
            @NotNull LocalTime startTime,
            @NotBlank String eventDetails
    ) {
    }
}
