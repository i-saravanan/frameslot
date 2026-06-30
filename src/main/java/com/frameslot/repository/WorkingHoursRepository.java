package com.frameslot.repository;

import com.frameslot.domain.Studio;
import com.frameslot.domain.WorkingHours;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {

    List<WorkingHours> findByStudio(Studio studio);

    Optional<WorkingHours> findByStudioAndDayOfWeek(Studio studio, DayOfWeek dayOfWeek);
}
