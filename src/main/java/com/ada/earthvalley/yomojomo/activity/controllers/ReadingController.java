package com.ada.earthvalley.yomojomo.activity.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.activity.dtos.CreateReadingRequest;
import com.ada.earthvalley.yomojomo.activity.dtos.ReadingListResponse;
import com.ada.earthvalley.yomojomo.activity.services.ReadingApiService;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.auth.YomojomoUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReadingController {
	private final ReadingApiService readingApiService;

	@GetMapping("/readings")
	public ResponseEntity<ReadingListResponse> fetchReadings(@YomojomoUser SecurityUser user) {
		return ResponseEntity.ok(readingApiService.fetchUsersReading(user));
	}

	@PostMapping("/articles/{articleId}/reading-contexts")
	public ResponseEntity<ReadingListResponse> fetchReadings(@YomojomoUser SecurityUser user,
		@RequestBody CreateReadingRequest createReadingRequest,
		@PathVariable Long articleId) {
		Long readingId = readingApiService.createReadingService(user, createReadingRequest, articleId);
		return ResponseEntity.created(URI.create("/api/articles/" + articleId + "readings/" + readingId)).build();
	}
}
