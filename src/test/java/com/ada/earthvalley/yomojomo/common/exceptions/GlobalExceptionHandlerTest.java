package com.ada.earthvalley.yomojomo.common.exceptions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ada.earthvalley.yomojomo.auth.JwtAuthenticationFilter;
import com.ada.earthvalley.yomojomo.common.exceptions.handler.GlobalExceptionHandler;

@WebMvcTest(
	controllers = ExceptionTestController.class,
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = {JwtAuthenticationFilter.class}
		)
	}
)
class GlobalExceptionHandlerTest {
	private MockMvc mockMvc;
	@MockBean
	private ErrorInfo errorInfo;

	@Autowired
	ExceptionTestController controller;

	@BeforeEach
	void initEach() {
		mockMvc = MockMvcBuilders
			.standaloneSetup(controller)
			.setControllerAdvice(GlobalExceptionHandler.class)
			.build();
	}

	@DisplayName("exception handler - 성공")
	@Test
	void exception_handler() throws Exception {
		final int code = 999;
		final int status = 400;
		final String message = "테스트 에러 메시지 입니다.";
		when(errorInfo.getCode()).thenReturn(code);
		when(errorInfo.getMessage()).thenReturn(message);
		when(errorInfo.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);

		ResultActions perform = mockMvc.perform(
			MockMvcRequestBuilders
				.get("/throws")
		);

		perform
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", message).exists())
			.andExpect(jsonPath("$.status", status).exists())
			.andExpect(jsonPath("$.code", code).exists())
			.andExpect(jsonPath("$.timestamp").exists())
			.andDo(print());
	}
}