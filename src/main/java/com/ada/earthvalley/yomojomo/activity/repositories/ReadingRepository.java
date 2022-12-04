package com.ada.earthvalley.yomojomo.activity.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.earthvalley.yomojomo.activity.entities.Reading;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
	List<Reading> findByUserId(UUID userId);
}
