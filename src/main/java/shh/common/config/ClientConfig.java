package shh.common.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import shh.alias.infrastructure.RandommerProperties;

@Configuration
@EnableConfigurationProperties(RandommerProperties.class)
@RequiredArgsConstructor
public class ClientConfig {

    private final RandommerProperties randommerProperties;

    @Bean(name = "randommerAliasRestClient")
    public RestClient tossPaymentRestClient() {
        return RestClient.builder()
                .baseUrl(randommerProperties.baseUrl())
                .defaultHeader("accept", "*/*")
                .defaultHeader("X-Api-Key", randommerProperties.secretKey())
                .requestFactory(getFactory())
                .build();
    }

    private HttpComponentsClientHttpRequestFactory getFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(randommerProperties.connectionTimeout());
        factory.setReadTimeout(randommerProperties.readTimeout());
        return factory;
    }
}
