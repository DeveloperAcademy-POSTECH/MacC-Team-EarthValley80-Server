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
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ada.earthvalley.yomojomo.common.exceptions.handler.GlobalExceptionHandler;
import com.ada.earthvalley.yomojomo.configs.SpringRestDocsConfig;
import com.ada.earthvalley.yomojomo.nie.NieTestConst;
import com.ada.earthvalley.yomojomo.nie.controllers.NieControllerV1;
import com.ada.earthvalley.yomojomo.nie.dtos.FetchNieResponse;
import com.ada.earthvalley.yomojomo.nie.fixtures.NieResponseFixture;
import com.ada.earthvalley.yomojomo.nie.services.NieFetchServiceV1;

@WebMvcTest(controllers = NieControllerV1.class)
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
	void Nie_??????_????????????() throws Exception {
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
			.andExpect(jsonPath("$.predict", is("??????")))
			.andExpect(jsonPath("$.where", is("??????")))
			.andExpect(jsonPath("$.why", is("???")))
			.andExpect(jsonPath("$.what", is("?????????")))
			.andExpect(jsonPath("$.when", is("??????")))
			.andExpect(jsonPath("$.who", is("??????")))
			.andExpect(jsonPath("$.how", is("?????????")))
			.andExpect(jsonPath("$.summary", is("??????")))
			.andDo(document(NieTestConst.DOCS_OUTPUT_DIR,
				responseFields(
					fieldWithPath("predict").description("??????"),
					fieldWithPath("where").description("??????"),
					fieldWithPath("why").description("???"),
					fieldWithPath("what").description("??????"),
					fieldWithPath("when").description("??????"),
					fieldWithPath("who").description("??????"),
					fieldWithPath("how").description("?????????"),
					fieldWithPath("summary").description("??????")
				)
			));

		verify(nieFetchService, times(1)).fetchNie(anyLong());
	}
}
