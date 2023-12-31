package com.bubblebubble.hr.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(title="프로젝트 api 명세서 나중에 수정하세요",
                description = "설명",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi(){

        String[] paths = {"/api/v1/**", "/auth/**"};
        return GroupedOpenApi.builder().group("프로젝트 api v1").pathsToMatch(paths).build();
    }
}
