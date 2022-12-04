package com.ada.earthvalley.yomojomo.user.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.earthvalley.yomojomo.user.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByUsername(String username);
}
