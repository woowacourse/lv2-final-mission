package finalmission.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.presentation.TossPaymentClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class TossPaymentClientConfig {
    @Bean
    public TossPaymentClient restClient(RestClient.Builder builder, ObjectMapper objectMapper,
                                        @Value("${payment.secretKey}") String secretKey) {
        return new TossPaymentClient(builder, objectMapper, secretKey);
    }

    @Bean
    public RestClientCustomizer restClientCustomizer() {
        return restClientBuilder -> {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(3_000);
            factory.setReadTimeout(10_000);
            restClientBuilder.requestFactory(factory)
                    .baseUrl("https://api.tosspayments.com");
        };
    }
}
