package com.eatin.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger {

	public static final Contact DEFAULT_CONTACT = new Contact("Mariana Samardzic",
			"https://github.com/marianasamardzic", "marianasamardzic2@gmai.com");

	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("EatIn Swagger ", "Documentation for EatIn Rest API",
			"1.0", "", DEFAULT_CONTACT, "", "", new ArrayList<VendorExtension>());

	@Bean
	Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.eatin"))
				.build()
				.apiInfo(DEFAULT_API_INFO).securitySchemes(Lists.newArrayList(apiKey()))
				.securityContexts(Arrays.asList(securityContext()));
	}

	private ApiKey apiKey() {
		return new ApiKey("apiKey", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("apiKey", authorizationScopes));
	}

}