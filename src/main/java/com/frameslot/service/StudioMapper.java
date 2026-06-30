package com.frameslot.service;

import com.frameslot.domain.Booking;
import com.frameslot.domain.SessionType;
import com.frameslot.domain.Studio;
import com.frameslot.web.dto.StudioDtos.BookingResponse;
import com.frameslot.web.dto.StudioDtos.SessionTypeResponse;
import com.frameslot.web.dto.StudioDtos.StudioResponse;

final class StudioMapper {

    private StudioMapper() {
    }

    static StudioResponse toStudioResponse(Studio studio) {
        return new StudioResponse(
                studio.getId(),
                studio.getOwner().getId(),
                studio.getName(),
                studio.getLocation(),
                studio.getBio(),
                studio.getInstagramLink(),
                studio.getStatus()
        );
    }

    static SessionTypeResponse toSessionTypeResponse(SessionType sessionType) {
        return new SessionTypeResponse(
                sessionType.getId(),
                sessionType.getStudio().getId(),
                sessionType.getName(),
                sessionType.getDurationHours(),
                sessionType.getPrice(),
                sessionType.getMaxAdvanceDays(),
                sessionType.isActive()
        );
    }

    static BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getStudio().getId(),
                booking.getStudio().getName(),
                booking.getCustomer().getId(),
                booking.getCustomer().getName(),
                booking.getCustomer().getEmail(),
                booking.getCustomer().getPhone(),
                booking.getSessionType().getId(),
                booking.getSessionType().getName(),
                booking.getBookingDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getEventDetails(),
                booking.getStatus(),
                booking.getCancellationReason()
        );
    }
}
