package com.ada.earthvalley.yomojomo.article.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.ada.earthvalley.yomojomo.article.entities.Article;
import com.ada.earthvalley.yomojomo.article.entities.Body;
import com.ada.earthvalley.yomojomo.article.entities.Section;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchArticleDetailResponse {
	private final Long id;
	private final String author;
	private final String title;
	private final List<SectionResponse> sectionList;

	public static FetchArticleDetailResponse ofArticle(Article article) {
		return FetchArticleDetailResponse.builder().id(article.getId()).author(article.getAuthor())
			.title(article.getTitle()).sectionList(article.getSectionList().stream()
				.map(section -> new SectionResponse(section))
				.collect(Collectors.toList())).build();
	}

	@Getter
	private static class SectionResponse {
		private final Long id;
		private final String tag;
		private final List<BodyResponse> bodyList;

		public SectionResponse(Section section) {
			this.id = section.getId();
			this.tag = section.getTag().getValue();
			this.bodyList = section.getBodyList().stream()
				.map(body -> new BodyResponse(body)).collect(Collectors.toList());
		}

		@Getter
		private static class BodyResponse {
			private final Long id;
			private final Long index;
			private final String content;

			public BodyResponse(Body body) {
				this.id = body.getId();
				this.index = body.getIndex();
				this.content = body.getContent();
			}
		}
	}

}
