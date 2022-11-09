package com.ada.earthvalley.yomojomo.nie.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ada.earthvalley.yomojomo.auth.JwtAuthenticationFilter;
import com.ada.earthvalley.yomojomo.common.exceptions.handler.GlobalExceptionHandler;
import com.ada.earthvalley.yomojomo.nie.dtos.FetchNieResponse;
import com.ada.earthvalley.yomojomo.nie.exceptions.NieError;
import com.ada.earthvalley.yomojomo.nie.exceptions.YomojomoNieException;
import com.ada.earthvalley.yomojomo.nie.services.NieFetchServiceV1;

@WebMvcTest(
	controllers = NieControllerV1.class,
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = {
				JwtAuthenticationFilter.class
			})
	}
)
class NieControllerV1Test {
	MockMvc mockMvc;
	@Autowired
	NieControllerV1 controller;
	@MockBean
	NieFetchServiceV1 nieFetchService;

	private static final String FETCH_URI = "/api/v1/nies/{nieId}";

	@BeforeEach
	void initEach() {
		mockMvc = MockMvcBuilders
			.standaloneSetup(controller)
			.setControllerAdvice(GlobalExceptionHandler.class)
			.build();
	}

	@DisplayName("fetch - 실패 (nie 없음)")
	@Test
	void fetch_fail() throws Exception {
		// given
		NieError errorInfo = NieError.NIE_NOT_FOUND;
		// when
		doThrow(new YomojomoNieException(errorInfo))
			.when(nieFetchService)
			.fetchNie(anyLong());

		// then
		mockMvc.perform(MockMvcRequestBuilders.get(FETCH_URI, 1L))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message", errorInfo.getMessage()).exists())
			.andExpect(jsonPath("$.status", errorInfo.getStatus()).exists())
			.andExpect(jsonPath("$.code", errorInfo.getCode()).exists())
			.andExpect(jsonPath("$.timestamp").exists());

		verify(nieFetchService, times(1)).fetchNie(anyLong());
	}

	@DisplayName("fetch - 성공 ")
	@Test
	void fetch_success() throws Exception {
		// given
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

		// when
		when(nieFetchService.fetchNie(anyLong()))
			.thenReturn(response);

		// then
		mockMvc.perform(
				MockMvcRequestBuilders.get(FETCH_URI, 1L))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.predict", is("예측")))
			.andExpect(jsonPath("$.where", is("어디")))
			.andExpect(jsonPath("$.why", is("왜")))
			.andExpect(jsonPath("$.what", is("무엇을")))
			.andExpect(jsonPath("$.when", is("언제")))
			.andExpect(jsonPath("$.who", is("누가")))
			.andExpect(jsonPath("$.how", is("어떻게")))
			.andExpect(jsonPath("$.summary", is("요약")));

		verify(nieFetchService, times(1)).fetchNie(anyLong());
	}
}