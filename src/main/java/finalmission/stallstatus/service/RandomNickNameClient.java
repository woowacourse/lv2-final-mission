package finalmission.stallstatus.service;

import finalmission.exception.ConnectApiException;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandomNickNameClient {

    private final RestClient.Builder restClientBuilder;

    public RandomNickNameClient(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }


    public String getRandomNickname() {
        RestClient restClient = setRestClient();
        String responseData = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("nameType", "firstname")
                        .queryParam("quantity", 1)
                        .build())
                .header("accept", "*/*")
                .header("X-Api-Key", "735cf8dca19e4fd38c5d57ccb45589c6")
                .retrieve()
                .body(String.class);
        if (responseData == null || responseData.isBlank()) {
            throw new ConnectApiException("외부 API가 올바르게 작동하지 않습니다");
        }
        return convertToNickname(responseData);
    }

    private RestClient setRestClient() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(5_000);
        factory.setReadTimeout(5_000);

        return restClientBuilder.baseUrl("https://randommer.io/api/Name")
                .requestFactory(factory)
                .build();
    }

    private String convertToNickname(String responseData) {
        System.out.println(responseData);
        return responseData.substring(2, responseData.length() - 2);
    }
}
