package com.isoft.code.urlshortner.commons.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocConfig {

    @Bean
    public OpenAPI openAPI(ApiDocInfo apiDocInfo) {
      return new OpenAPI()
          .info(new Info().title(apiDocInfo.getTitle())
              .description(apiDocInfo.getDescription())
              .version(apiDocInfo.getVersion())
              .license(new License().name("Apache 2.0")));
    }
}
