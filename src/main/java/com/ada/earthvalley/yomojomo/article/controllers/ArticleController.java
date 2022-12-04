package com.ada.earthvalley.yomojomo.article.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.article.dtos.FetchArticleListResponse;
import com.ada.earthvalley.yomojomo.article.services.ArticleApiService;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;
import com.ada.earthvalley.yomojomo.auth.YomojomoUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/article")
@RestController
public class ArticleController {
	private final ArticleApiService articleApiService;

	@GetMapping()
	public ResponseEntity<FetchArticleListResponse> getPersonalizedArticle(@YomojomoUser SecurityUser user) {
		return ResponseEntity.ok(articleApiService.getPersonalizedArticleLists(user));
	}
}
