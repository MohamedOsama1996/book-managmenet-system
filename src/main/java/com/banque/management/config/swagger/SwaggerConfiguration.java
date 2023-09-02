package com.banque.management.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@EnableWebMvc
public class SwaggerConfiguration implements WebMvcConfigurer {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)  .securityContexts(asList(securityContext()))
                                                    .securitySchemes(asList(new ApiKey("Authorization",
                                                                                       "Authorization",
                                                                                       "header")))
                                                    .select()
                                                  .apis(RequestHandlerSelectors.basePackage("com.banque.management"))
                                                  .paths(PathSelectors.regex("/.*"))
                                                  .build().apiInfo(apiInfoMetaData());
  }

  private ApiInfo apiInfoMetaData() {

    return new ApiInfoBuilder().title("Book Management System")
                               .description("A System for book management")
                               .version("1.0.0")
                               .build();
  }

  @Bean
  SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder()
                                       .scopeSeparator(",")
                                       .additionalQueryStringParams(null)
                                       .useBasicAuthenticationWithAccessCodeGrant(false)
                                       .build();
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
                          .securityReferences(defaultAuth())
                          .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return asList(new SecurityReference("Authorization", authorizationScopes));
  }
}