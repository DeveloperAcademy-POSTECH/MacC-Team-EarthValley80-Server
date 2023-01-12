package com.ada.earthvalley.yomojomo.article.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ada.earthvalley.yomojomo.article.entities.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}
