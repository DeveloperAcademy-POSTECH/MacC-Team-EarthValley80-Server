package com.ada.earthvalley.yomojomo.configs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;

public class SpringRestDocsConfig {
	public static MockMvcConfigurer configurer(RestDocumentationContextProvider restDocumentation) {
		return documentationConfiguration(restDocumentation)
			.uris()
			// TODO: 배포 후 application property 에서 가져와 설정하기
			.withScheme("http")
			.withHost("localhost")
			.withPort(8080)
			.and()
			.operationPreprocessors()
			.withRequestDefaults(prettyPrint())
			.withResponseDefaults(prettyPrint());
	}

}
