package com.ada.earthvalley.yomojomo.nie.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ada.earthvalley.yomojomo.auth.JwtAuthenticationFilter;
import com.ada.earthvalley.yomojomo.common.exceptions.handler.GlobalExceptionHandler;
import com.ada.earthvalley.yomojomo.nie.NieTestConst;
import com.ada.earthvalley.yomojomo.nie.controllers.NieControllerV1;
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
class NieControllerV1FailureTest {
	MockMvc mockMvc;
	@Autowired
	NieControllerV1 controller;
	@MockBean
	NieFetchServiceV1 nieFetchService;

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
		mockMvc.perform(MockMvcRequestBuilders.get(NieTestConst.FETCH_URI, 1L))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message", errorInfo.getMessage()).exists())
			.andExpect(jsonPath("$.status", errorInfo.getStatus()).exists())
			.andExpect(jsonPath("$.code", errorInfo.getCode()).exists())
			.andExpect(jsonPath("$.timestamp").exists());

		verify(nieFetchService, times(1)).fetchNie(anyLong());
	}
}