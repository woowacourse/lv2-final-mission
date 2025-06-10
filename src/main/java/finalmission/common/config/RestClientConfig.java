package finalmission.common.config;

import finalmission.external.NicknameExceptionHandler;
import finalmission.external.RandomNicknameGateway;
import finalmission.external.RandommerNicknameGateway;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(RandomNicknameProperties.class)
public class RestClientConfig {

    private final RandomNicknameProperties randomNicknameProperties;

    public RestClientConfig(final RandomNicknameProperties randomNicknameProperties) {
        this.randomNicknameProperties = randomNicknameProperties;
    }

    @Bean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder.build();
    }

    @Bean
    public RestClientCustomizer restClientCustomizer() {
        return (restClientBuilder) -> {
            restClientBuilder.requestFactory(clientHttpRequestFactory())
                    .baseUrl(randomNicknameProperties.getBaseUrl());
        };
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(30000);
        return factory;
    }

    @Bean
    public NicknameExceptionHandler nicknameExceptionHandler() {
        return new NicknameExceptionHandler();
    }
}
