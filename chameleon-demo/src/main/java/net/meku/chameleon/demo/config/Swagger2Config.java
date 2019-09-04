package net.meku.chameleon.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ConditionalOnProperty(name = "app.swagger.enable", havingValue = "true", matchIfMissing = true)
@Configuration
@EnableSwagger2
public class Swagger2Config {

    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.meku.chameleon.demo.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Demo APIs")
                                   .description("REST APIs for Chameleon Demo")
                                   .termsOfServiceUrl("https://bitbucket.org/shoopman/chameleon")
                                   .version("1.0.0")
                                   .contact(new Contact("Bin", "Admin", "admin@meku.net"))
                                   .build();
    }

}
