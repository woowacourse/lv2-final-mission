package finalmission.client.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class SpcdeInfoConfig {

    private final String serviceKey;

    public SpcdeInfoConfig(@Value("${spcde-info.service-key}") String serviceKey) {
        this.serviceKey = serviceKey;
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);

        return RestClient.builder()
                .requestFactory(factory)
                .defaultHeader("Content-Type", "application/json");
    }

    @Bean
    public RestClient tossRestClient(RestClient.Builder restClientBuilder) {
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString((serviceKey + ":").getBytes(StandardCharsets.UTF_8));

        return restClientBuilder
                .baseUrl("http://apis.data.go.kr/B090041/openapi/service")
                .defaultHeader("Authorization", encodedAuth)
                .build();
    }
}
