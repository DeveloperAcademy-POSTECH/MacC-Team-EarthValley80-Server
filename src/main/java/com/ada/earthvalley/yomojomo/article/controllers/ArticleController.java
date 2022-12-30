package com.ada.earthvalley.yomojomo.article.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.article.dtos.FetchArticleDetailResponse;
import com.ada.earthvalley.yomojomo.article.dtos.FetchArticleListResponse;
import com.ada.earthvalley.yomojomo.article.dtos.SaveReactionRequest;
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

	// TODO: 위 api와 합칠 예정
	@GetMapping("/weekly")
	public ResponseEntity<FetchArticleListResponse> getWeeklyArticle() {
		return ResponseEntity.ok(articleApiService.getWeeklyArticleLists());
	}

	@GetMapping("/{articleId}")
	public ResponseEntity<FetchArticleDetailResponse> getArticleDetail(@PathVariable Long articleId) {
		return ResponseEntity.ok(articleApiService.getArticleDetail(articleId));
	}

	@PostMapping("/{articleId}/reactions")
	public ResponseEntity saveUserReactions(@PathVariable Long articleId, @YomojomoUser SecurityUser user,
		SaveReactionRequest saveReactionRequest) {
		articleApiService.saveArticleReaction(articleId, user, saveReactionRequest);
		return ResponseEntity.ok(URI.create("/api/articles/{articleId}/reactions"));
	}
}
