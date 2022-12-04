package com.ada.earthvalley.yomojomo.activity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.earthvalley.yomojomo.activity.entities.Activity;
import com.ada.earthvalley.yomojomo.activity.entities.enums.ActivityStatus;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
	boolean existsByArticleIdAndStatus(Long articleId, ActivityStatus status);
}
