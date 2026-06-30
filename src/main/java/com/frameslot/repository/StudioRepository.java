package com.frameslot.repository;

import com.frameslot.domain.Studio;
import com.frameslot.domain.StudioStatus;
import com.frameslot.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository<Studio, Long> {

    List<Studio> findByStatus(StudioStatus status);

    Optional<Studio> findFirstByOwner(User owner);

    List<Studio> findByOwner(User owner);
}
