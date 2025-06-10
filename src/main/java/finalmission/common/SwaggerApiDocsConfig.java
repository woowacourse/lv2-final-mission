package finalmission.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerApiDocsConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        String title = "PopupStore Application Swagger";
        String description = "팝업 스토어 예약 서비스의 API 문서입니다.";

        Info info = new Info().title(title).description(description).version("1.0.0");

        return new OpenAPI().info(info);
    }
}
