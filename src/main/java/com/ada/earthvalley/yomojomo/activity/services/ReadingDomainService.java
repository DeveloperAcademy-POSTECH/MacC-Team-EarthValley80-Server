package com.ada.earthvalley.yomojomo.activity.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.activity.entities.Reading;
import com.ada.earthvalley.yomojomo.activity.entities.enums.ActivityStatus;
import com.ada.earthvalley.yomojomo.activity.repositories.ActivityRepository;
import com.ada.earthvalley.yomojomo.activity.repositories.ReadingRepository;
import com.ada.earthvalley.yomojomo.auth.SecurityUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReadingDomainService {
	private final ReadingRepository readingRepository;
	private final ActivityRepository activityRepository;

	public List<Reading> fetchUsersReadingActivityNotFinished(SecurityUser user) {
		// 유저의 Reading 리스트
		return readingRepository.findByUserId(user.getId())
			.stream()
			// Activity 를 마쳤을 경우 제외
			.filter(reading -> !activityRepository.existsByArticleIdAndStatus(reading.getArticleId(),
				ActivityStatus.FINISHED))
			.collect(Collectors.toList());
	}
}
