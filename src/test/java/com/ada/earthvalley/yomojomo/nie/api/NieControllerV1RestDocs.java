package com.ada.earthvalley.yomojomo.nie.api;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ada.earthvalley.yomojomo.auth.JwtAuthenticationFilter;
import com.ada.earthvalley.yomojomo.common.exceptions.handler.GlobalExceptionHandler;
import com.ada.earthvalley.yomojomo.configs.SpringRestDocsConfig;
import com.ada.earthvalley.yomojomo.nie.NieTestConst;
import com.ada.earthvalley.yomojomo.nie.controllers.NieControllerV1;
import com.ada.earthvalley.yomojomo.nie.dtos.FetchNieResponse;
import com.ada.earthvalley.yomojomo.nie.fixtures.NieResponseFixture;
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
@ExtendWith({RestDocumentationExtension.class})
public class NieControllerV1RestDocs {
	MockMvc mockMvc;
	@Autowired
	NieControllerV1 controller;
	@MockBean
	NieFetchServiceV1 nieFetchService;

	@BeforeEach
	void initEach(RestDocumentationContextProvider restDocumentation) {
		mockMvc = MockMvcBuilders
			.standaloneSetup(controller)
			.setControllerAdvice(GlobalExceptionHandler.class)
			.apply(SpringRestDocsConfig.configurer(restDocumentation))
			.build();
	}

	@DisplayName("[GET] /v1/nies/{nieId}")
	@Test
	void Nie_단일_가져오기() throws Exception {
		// given
		FetchNieResponse response = NieResponseFixture.fetchNieResponseFixture();

		// when
		when(nieFetchService.fetchNie(anyLong()))
			.thenReturn(response);

		// then
		mockMvc.perform(
				RestDocumentationRequestBuilders.get(NieTestConst.FETCH_URI, 1L))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.predict", is("예측")))
			.andExpect(jsonPath("$.where", is("어디")))
			.andExpect(jsonPath("$.why", is("왜")))
			.andExpect(jsonPath("$.what", is("무엇을")))
			.andExpect(jsonPath("$.when", is("언제")))
			.andExpect(jsonPath("$.who", is("누가")))
			.andExpect(jsonPath("$.how", is("어떻게")))
			.andExpect(jsonPath("$.summary", is("요약")))
			.andDo(document(NieTestConst.DOCS_OUTPUT_DIR,
				responseFields(
					fieldWithPath("predict").description("예측"),
					fieldWithPath("where").description("어디"),
					fieldWithPath("why").description("왜"),
					fieldWithPath("what").description("무엇"),
					fieldWithPath("when").description("언제"),
					fieldWithPath("who").description("누가"),
					fieldWithPath("how").description("어떻게"),
					fieldWithPath("summary").description("요약")
				)
			));

		verify(nieFetchService, times(1)).fetchNie(anyLong());
	}
}
