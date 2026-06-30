package com.frameslot.service;

import com.frameslot.domain.Booking;
import com.frameslot.domain.Notification;
import com.frameslot.domain.NotificationType;
import com.frameslot.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void recordBookingEmail(Booking booking, NotificationType type) {
        notificationRepository.save(new Notification(booking, type));
        LOGGER.info("Notification {} recorded for booking {}", type, booking.getId());
    }
}
