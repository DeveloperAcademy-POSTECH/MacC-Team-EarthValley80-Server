package com.ada.earthvalley.yomojomo.nie.fixtures;

import java.lang.reflect.Constructor;

import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.nie.dtos.FetchNieResponse;

public class NieResponseFixture {
	public static FetchNieResponse fetchNieResponseFixture() throws Exception {
		Constructor<FetchNieResponse> constructor = FetchNieResponse.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		FetchNieResponse response = constructor.newInstance();

		ReflectionTestUtils.setField(response, "predict", "예측");
		ReflectionTestUtils.setField(response, "where", "어디");
		ReflectionTestUtils.setField(response, "why", "왜");
		ReflectionTestUtils.setField(response, "what", "무엇을");
		ReflectionTestUtils.setField(response, "when", "언제");
		ReflectionTestUtils.setField(response, "who", "누가");
		ReflectionTestUtils.setField(response, "how", "어떻게");
		ReflectionTestUtils.setField(response, "summary", "요약");
		return response;
	}
}
