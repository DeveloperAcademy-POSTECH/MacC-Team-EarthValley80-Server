package com.ada.earthvalley.yomojomo.nie.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.nie.services.NieFetchServiceV1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class NieControllerV1 {
	private final NieFetchServiceV1 nieFetchService;

	@GetMapping("/nies/{nieId}")
	public ResponseEntity fetch(
		@PathVariable final Long nieId
	) {
		return ResponseEntity.ok(nieFetchService.fetchNie(nieId));
	}
}
