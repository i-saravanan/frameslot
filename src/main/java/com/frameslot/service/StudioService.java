package com.frameslot.service;

import com.frameslot.domain.BlockedDate;
import com.frameslot.domain.Booking;
import com.frameslot.domain.BookingStatus;
import com.frameslot.domain.Role;
import com.frameslot.domain.SessionType;
import com.frameslot.domain.Studio;
import com.frameslot.domain.StudioStatus;
import com.frameslot.domain.User;
import com.frameslot.domain.WorkingHours;
import com.frameslot.repository.BlockedDateRepository;
import com.frameslot.repository.BookingRepository;
import com.frameslot.repository.SessionTypeRepository;
import com.frameslot.repository.StudioRepository;
import com.frameslot.repository.WorkingHoursRepository;
import com.frameslot.web.ApiException;
import com.frameslot.web.dto.StudioDtos.AvailableSlotsResponse;
import com.frameslot.web.dto.StudioDtos.BlockedDateRequest;
import com.frameslot.web.dto.StudioDtos.BookingResponse;
import com.frameslot.web.dto.StudioDtos.DashboardResponse;
import com.frameslot.web.dto.StudioDtos.SessionTypeRequest;
import com.frameslot.web.dto.StudioDtos.SessionTypeResponse;
import com.frameslot.web.dto.StudioDtos.SlotResponse;
import com.frameslot.web.dto.StudioDtos.StudioRequest;
import com.frameslot.web.dto.StudioDtos.StudioResponse;
import com.frameslot.web.dto.StudioDtos.WorkingHoursRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudioService {

    private final CurrentUserService currentUserService;
    private final StudioRepository studioRepository;
    private final SessionTypeRepository sessionTypeRepository;
    private final WorkingHoursRepository workingHoursRepository;
    private final BlockedDateRepository blockedDateRepository;
    private final BookingRepository bookingRepository;

    public StudioService(CurrentUserService currentUserService, StudioRepository studioRepository,
                         SessionTypeRepository sessionTypeRepository, WorkingHoursRepository workingHoursRepository,
                         BlockedDateRepository blockedDateRepository, BookingRepository bookingRepository) {
        this.currentUserService = currentUserService;
        this.studioRepository = studioRepository;
        this.sessionTypeRepository = sessionTypeRepository;
        this.workingHoursRepository = workingHoursRepository;
        this.blockedDateRepository = blockedDateRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public StudioResponse registerStudio(Long ownerId, StudioRequest request) {
        User owner = currentUserService.requireUser(ownerId, Role.OWNER);
        Studio studio = new Studio(owner, request.name(), request.location(), request.bio(), request.instagramLink());
        return StudioMapper.toStudioResponse(studioRepository.save(studio));
    }

    @Transactional
    public StudioResponse updateProfile(Long ownerId, StudioRequest request) {
        Studio studio = requireOwnerStudio(ownerId);
        studio.updateProfile(request.name(), request.location(), request.bio(), request.instagramLink());
        return StudioMapper.toStudioResponse(studio);
    }

    @Transactional
    public SessionTypeResponse addSessionType(Long ownerId, SessionTypeRequest request) {
        if (request.durationHours() > 23) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Session duration must be less than 24 hours");
        }
        Studio studio = requireOwnerStudio(ownerId);
        SessionType sessionType = new SessionType(
                studio,
                request.name(),
                request.durationHours(),
                request.price(),
                request.maxAdvanceDays()
        );
        return StudioMapper.toSessionTypeResponse(sessionTypeRepository.save(sessionType));
    }

    @Transactional
    public void addWorkingHours(Long ownerId, WorkingHoursRequest request) {
        if (!request.openTime().isBefore(request.closeTime())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Opening time must be before closing time");
        }
        Studio studio = requireOwnerStudio(ownerId);
        workingHoursRepository.save(new WorkingHours(studio, request.dayOfWeek(), request.openTime(), request.closeTime()));
    }

    @Transactional
    public void blockDate(Long ownerId, BlockedDateRequest request) {
        Studio studio = requireOwnerStudio(ownerId);
        blockedDateRepository.save(new BlockedDate(studio, request.date(), request.reason()));
    }

    @Transactional(readOnly = true)
    public DashboardResponse dashboard(Long ownerId) {
        Studio studio = requireOwnerStudio(ownerId);
        LocalDate today = LocalDate.now();
        LocalDate weekEnd = today.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
        LocalDate monthEnd = today.with(TemporalAdjusters.lastDayOfMonth());
        List<Booking> upcoming = bookingRepository.findByStudioAndBookingDateBetweenOrderByBookingDateAscStartTimeAsc(
                studio,
                today,
                monthEnd
        );
        return new DashboardResponse(
                upcoming.stream().filter(booking -> booking.getBookingDate().equals(today)).count(),
                upcoming.stream().filter(booking -> !booking.getBookingDate().isAfter(weekEnd)).count(),
                upcoming.size(),
                upcoming.stream().map(StudioMapper::toBookingResponse).toList()
        );
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> ownerBookings(Long ownerId) {
        Studio studio = requireOwnerStudio(ownerId);
        return bookingRepository.findByStudioOrderByBookingDateAscStartTimeAsc(studio).stream()
                .map(StudioMapper::toBookingResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StudioResponse> activeStudios() {
        return studioRepository.findByStatus(StudioStatus.ACTIVE).stream()
                .map(StudioMapper::toStudioResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SessionTypeResponse> activeSessions(Long studioId) {
        Studio studio = requireActiveStudio(studioId);
        return sessionTypeRepository.findByStudioAndActiveTrue(studio).stream()
                .map(StudioMapper::toSessionTypeResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AvailableSlotsResponse availableSlots(Long studioId, Long sessionTypeId, LocalDate date) {
        Studio studio = requireActiveStudio(studioId);
        SessionType sessionType = requireSessionTypeForStudio(studio, sessionTypeId);
        if (date.isAfter(LocalDate.now().plusDays(sessionType.getMaxAdvanceDays()))) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Date is outside the max advance booking window");
        }
        if (blockedDateRepository.existsByStudioAndDate(studio, date)) {
            return new AvailableSlotsResponse(studioId, sessionTypeId, date, List.of());
        }
        List<SlotResponse> slots = buildSlots(studio, sessionType, date);
        return new AvailableSlotsResponse(studioId, sessionTypeId, date, slots);
    }

    public Studio requireActiveStudio(Long studioId) {
        Studio studio = studioRepository.findById(studioId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Studio not found"));
        if (studio.getStatus() != StudioStatus.ACTIVE) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Studio is not active");
        }
        return studio;
    }

    public SessionType requireSessionTypeForStudio(Studio studio, Long sessionTypeId) {
        SessionType sessionType = sessionTypeRepository.findById(sessionTypeId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Session type not found"));
        if (!sessionType.getStudio().getId().equals(studio.getId()) || !sessionType.isActive()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Session type does not belong to this active studio");
        }
        return sessionType;
    }

    public Studio requireOwnerStudio(Long ownerId) {
        User owner = currentUserService.requireUser(ownerId, Role.OWNER);
        return studioRepository.findFirstByOwner(owner)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Owner has not registered a studio"));
    }

    private List<SlotResponse> buildSlots(Studio studio, SessionType sessionType, LocalDate date) {
        int durationHours = sessionType.getDurationHours();
        return java.util.stream.IntStream.iterate(0, hour -> hour + durationHours)
                .takeWhile(startHour -> startHour + durationHours < 24)
                .mapToObj(hour -> LocalTime.MIDNIGHT.plusHours(hour))
                .filter(start -> bookingRepository.findOverlappingBookings(
                        studio,
                        date,
                        start,
                        start.plusHours(sessionType.getDurationHours()),
                        List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
                ).isEmpty())
                .map(start -> new SlotResponse(start, start.plusHours(sessionType.getDurationHours())))
                .toList();
    }
}
