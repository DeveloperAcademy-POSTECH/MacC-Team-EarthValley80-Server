package com.ada.earthvalley.yomojomo.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.earthvalley.yomojomo.user.entities.UserTopic;

public interface UserTopicRepository extends JpaRepository<UserTopic, Long> {
}
