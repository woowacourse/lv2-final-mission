package finalmission.configuration;

import finalmission.infrastructure.RandomNameClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfiguration {

    private static final String baseUrl = "https://randommer.io/api";
    private static final String tokenValue = "a05ec5d252af4b4fa6a17956f6f28beb";

    @Bean
    public RestClient.Builder restClientBuilder() {
        var clientFactory = new HttpComponentsClientHttpRequestFactory();
        clientFactory.setConnectTimeout(1_200);
        clientFactory.setReadTimeout(6_000);

        return RestClient.builder()
                .requestFactory(clientFactory)
                .baseUrl(baseUrl)
                .defaultHeader("X-Api-Key", tokenValue);
    }

    @Bean
    public RestClient restClient() {
        Builder builder = restClientBuilder();
        return builder.build();
    }

    @Bean
    public RandomNameClientService createMailService() {
        RestClient restClient = restClient();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(RandomNameClientService.class);
    }
}
