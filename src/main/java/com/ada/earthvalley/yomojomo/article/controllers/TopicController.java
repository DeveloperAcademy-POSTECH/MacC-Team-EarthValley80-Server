package com.ada.earthvalley.yomojomo.article.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.article.dtos.SelectInterestsRequest;
import com.ada.earthvalley.yomojomo.article.services.TopicService;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.auth.YomojomoUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class TopicController {
	private final TopicService topicService;

	@GetMapping("/topics")
	public ResponseEntity fetchSubtopics() {
		return ResponseEntity.ok(topicService.fetchAllTopics());
	}

	@PostMapping("/interests")
	public ResponseEntity selectInterests(
		@YomojomoUser SecurityUser user,
		@RequestBody SelectInterestsRequest body
	) {
		topicService.saveAllTopics(user, body);
		return ResponseEntity.created(URI.create("/api/interests")).build();
	}
}
