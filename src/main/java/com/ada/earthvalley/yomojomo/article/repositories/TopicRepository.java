package com.ada.earthvalley.yomojomo.article.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ada.earthvalley.yomojomo.article.entities.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
