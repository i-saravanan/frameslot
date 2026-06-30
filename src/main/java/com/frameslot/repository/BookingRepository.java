package com.frameslot.repository;

import com.frameslot.domain.Booking;
import com.frameslot.domain.BookingStatus;
import com.frameslot.domain.Studio;
import com.frameslot.domain.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerOrderByBookingDateDescStartTimeDesc(User customer);

    List<Booking> findByStudioOrderByBookingDateAscStartTimeAsc(Studio studio);

    List<Booking> findByStudioAndBookingDateBetweenOrderByBookingDateAscStartTimeAsc(
            Studio studio,
            LocalDate from,
            LocalDate to
    );

    @Query("""
            select b from Booking b
            where b.studio = :studio
              and b.bookingDate = :date
              and b.status in :statuses
              and b.startTime < :endTime
              and b.endTime > :startTime
            """)
    List<Booking> findOverlappingBookings(
            @Param("studio") Studio studio,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("statuses") Collection<BookingStatus> statuses
    );
}
