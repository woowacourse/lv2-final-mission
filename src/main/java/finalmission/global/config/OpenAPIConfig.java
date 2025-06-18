package finalmission.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Final Mission API")
                .version("1.0.0")
                .description("최종 미션 API 명세서");

        return new OpenAPI()
                .info(info);
    }

    @Bean
    public GroupedOpenApi memberAPI() {
        return GroupedOpenApi.builder()
                .group("회원")
                .pathsToMatch("/members/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reservationAPI() {
        return GroupedOpenApi.builder()
                .group("회의실 예약")
                .pathsToMatch("/reservations/**")
                .build();
    }
}
