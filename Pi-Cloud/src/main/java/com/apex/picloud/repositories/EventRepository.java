package com.apex.picloud.repositories;

import com.apex.picloud.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
