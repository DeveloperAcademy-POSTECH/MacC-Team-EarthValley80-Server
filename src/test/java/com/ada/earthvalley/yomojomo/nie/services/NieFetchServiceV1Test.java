package com.ada.earthvalley.yomojomo.nie.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.ada.earthvalley.yomojomo.nie.dtos.FetchNieResponse;
import com.ada.earthvalley.yomojomo.nie.entities.Nie;
import com.ada.earthvalley.yomojomo.nie.entities.NieProcess;
import com.ada.earthvalley.yomojomo.nie.entities.enums.NieProcessType;
import com.ada.earthvalley.yomojomo.nie.exceptions.YomojomoNieException;
import com.ada.earthvalley.yomojomo.nie.repositories.NieRepository;

@ExtendWith(MockitoExtension.class)
class NieFetchServiceV1Test {
	@Mock
	NieRepository nieRepository;

	@InjectMocks
	NieFetchServiceV1 nieFetchService;

	Nie nie1;

	@BeforeEach
	void initEach() throws
		NoSuchMethodException,
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		Constructor<Nie> nieConstructor = Nie.class.getDeclaredConstructor();
		nieConstructor.setAccessible(true);
		nie1 = nieConstructor.newInstance();
		NieProcess how = new NieProcess(null, null, NieProcessType.HOW, "어떻게");
		NieProcess predict = new NieProcess(null, null, NieProcessType.PREDICT, "예측");
		NieProcess what = new NieProcess(null, null, NieProcessType.WHAT, "무엇을");
		NieProcess summary = new NieProcess(null, null, NieProcessType.SUMMARY, "요약");
		NieProcess when = new NieProcess(null, null, NieProcessType.WHEN, "언제");
		NieProcess where = new NieProcess(null, null, NieProcessType.WHERE, "어디서");
		NieProcess who = new NieProcess(null, null, NieProcessType.WHO, "누가");
		NieProcess why = new NieProcess(null, null, NieProcessType.WHY, "왜");
		ReflectionTestUtils.setField(nie1, "processes",
			Arrays.asList(how, predict, what, summary, when, where, who, why));
	}

	@DisplayName("fetchNie - 실패 (nie 없음)")
	@Test
	void fetch_nie_fail_not_found() {
		// when
		when(nieRepository.findById(anyLong()))
			.thenReturn(Optional.empty());

		// then
		assertThatThrownBy(() -> nieFetchService.fetchNie(1L))
			.isInstanceOf(YomojomoNieException.class);
	}

	@DisplayName("fetchNie - 성공")
	@Test
	void fetch_nie_success() {
		// when
		when(nieRepository.findById(anyLong()))
			.thenReturn(Optional.of(nie1));
		FetchNieResponse response = nieFetchService.fetchNie(1L);

		// then
		assertNotNull(response);
		assertThat(response.getHow()).isEqualTo("어떻게");
		assertThat(response.getWhen()).isEqualTo("언제");
		assertThat(response.getWhere()).isEqualTo("어디서");
		assertThat(response.getWhat()).isEqualTo("무엇을");
		assertThat(response.getSummary()).isEqualTo("요약");
		assertThat(response.getPredict()).isEqualTo("예측");
		assertThat(response.getWhy()).isEqualTo("왜");
		assertThat(response.getWho()).isEqualTo("누가");
	}

}