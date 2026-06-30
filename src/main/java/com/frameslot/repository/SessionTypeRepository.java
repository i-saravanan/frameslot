package com.frameslot.repository;

import com.frameslot.domain.SessionType;
import com.frameslot.domain.Studio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionTypeRepository extends JpaRepository<SessionType, Long> {

    List<SessionType> findByStudioAndActiveTrue(Studio studio);

    List<SessionType> findByStudio(Studio studio);
}
