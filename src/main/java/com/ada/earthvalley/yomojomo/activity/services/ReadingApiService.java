package com.ada.earthvalley.yomojomo.activity.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ada.earthvalley.yomojomo.activity.dtos.ReadingListResponse;
import com.ada.earthvalley.yomojomo.activity.entities.Reading;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReadingApiService {
	private final ReadingDomainService readingDomainService;
	private final ArticleDomainService articleDomainService;

	public ReadingListResponse fetchUsersReading(SecurityUser user) {
		List<Reading> readings = readingDomainService.fetchUsersReadingActivityNotFinished(user);
		return new ReadingListResponse(articleDomainService.articlesFromReadings(readings));
	}
}
