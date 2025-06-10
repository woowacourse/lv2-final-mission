package finalmission.global.provider;

import finalmission.global.config.ApiProperties;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

public class RestClientProvider {

    public static RestClient.Builder createRestClient(ApiProperties apiProperties) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(apiProperties.getConnectTimeout());
        factory.setReadTimeout(apiProperties.getReadTimeout());

        return RestClient.builder()
                .requestFactory(factory)
                .baseUrl(apiProperties.getBaseUrl());
    }
}
