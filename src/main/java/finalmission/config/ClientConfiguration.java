package finalmission.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {

    @Value("${holiday.api.service-key}")
    private String serviceKey;

    @Bean
    @Qualifier("holidayClient")
    public RestClient holidayClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService")
                .requestFactory(getRequestFactory())
                .build();
    }

    public SimpleClientHttpRequestFactory getRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(5));
        requestFactory.setReadTimeout(Duration.ofSeconds(5));
        return requestFactory;
    }

    public String getServiceKey() {
        return serviceKey;
    }
}