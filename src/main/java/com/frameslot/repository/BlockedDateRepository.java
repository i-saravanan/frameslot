package com.frameslot.repository;

import com.frameslot.domain.BlockedDate;
import com.frameslot.domain.Studio;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedDateRepository extends JpaRepository<BlockedDate, Long> {

    boolean existsByStudioAndDate(Studio studio, LocalDate date);

    List<BlockedDate> findByStudio(Studio studio);
}
