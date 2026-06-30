package com.frameslot.service;

import com.frameslot.domain.Role;
import com.frameslot.domain.Studio;
import com.frameslot.domain.StudioStatus;
import com.frameslot.repository.BookingRepository;
import com.frameslot.repository.StudioRepository;
import com.frameslot.web.ApiException;
import com.frameslot.web.dto.StudioDtos.BookingResponse;
import com.frameslot.web.dto.StudioDtos.StudioResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final CurrentUserService currentUserService;
    private final StudioRepository studioRepository;
    private final BookingRepository bookingRepository;

    public AdminService(CurrentUserService currentUserService, StudioRepository studioRepository,
                        BookingRepository bookingRepository) {
        this.currentUserService = currentUserService;
        this.studioRepository = studioRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(readOnly = true)
    public List<StudioResponse> studios(Long adminId) {
        currentUserService.requireUser(adminId, Role.ADMIN);
        return studioRepository.findAll().stream()
                .map(StudioMapper::toStudioResponse)
                .toList();
    }

    @Transactional
    public StudioResponse approveStudio(Long adminId, Long studioId) {
        currentUserService.requireUser(adminId, Role.ADMIN);
        Studio studio = requireStudio(studioId);
        studio.setStatus(StudioStatus.ACTIVE);
        return StudioMapper.toStudioResponse(studio);
    }

    @Transactional
    public StudioResponse deactivateStudio(Long adminId, Long studioId) {
        currentUserService.requireUser(adminId, Role.ADMIN);
        Studio studio = requireStudio(studioId);
        studio.setStatus(StudioStatus.INACTIVE);
        return StudioMapper.toStudioResponse(studio);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> bookings(Long adminId) {
        currentUserService.requireUser(adminId, Role.ADMIN);
        return bookingRepository.findAll().stream()
                .map(StudioMapper::toBookingResponse)
                .toList();
    }

    private Studio requireStudio(Long studioId) {
        return studioRepository.findById(studioId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Studio not found"));
    }
}
