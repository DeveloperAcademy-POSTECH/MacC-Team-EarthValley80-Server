package com.ada.earthvalley.yomojomo.activity.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.activity.dtos.CreateActivityRequest;
import com.ada.earthvalley.yomojomo.activity.services.ActivityService;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.auth.YomojomoUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ActivityController {
	private final ActivityService activityService;

	@GetMapping("/activities")
	public ResponseEntity fetchActivities(@YomojomoUser SecurityUser user) {
		return ResponseEntity.ok(activityService.fetchActivities(user));
	}

	@PostMapping("/articles/{articleId}/activities")
	public ResponseEntity createActivity(
		@YomojomoUser SecurityUser user,
		@PathVariable Long articleId,
		@RequestBody CreateActivityRequest body
	) {
		activityService.createActivity(user, articleId, body);
		return ResponseEntity.created(URI.create("")).build();
	}
}
