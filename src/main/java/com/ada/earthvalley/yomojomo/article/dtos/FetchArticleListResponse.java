package com.ada.earthvalley.yomojomo.article.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.ada.earthvalley.yomojomo.article.entities.Article;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchArticleListResponse {
	private final List<ArticleInfoResponse> articleInfoResponseList;

	public static FetchArticleListResponse ofList(List<Article> articleList) {
		return new FetchArticleListResponse(
			articleList.stream()
				.map(article -> new ArticleInfoResponse(article))
				.collect(Collectors.toList()));
	}

	@Getter
	private static class ArticleInfoResponse {
		private final long id;
		private final String title;

		// TODO: subTopic보내주는건지, majorTopic인지 확인하기 (2022.11.04 - Daon)
		private final String majorTopic;

		private final String imageUrl;

		public ArticleInfoResponse(Article article) {
			this.id = article.getId();
			this.title = article.getTitle();
			this.imageUrl = article.getImageUrl();
			this.majorTopic = article.getMajorTopic();
		}
	}
}
