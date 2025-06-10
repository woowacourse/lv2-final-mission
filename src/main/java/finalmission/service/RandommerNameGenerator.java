package finalmission.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RandommerNameGenerator implements NameGenerator {

    private final RestClient restClient;
    private final String apiKey;

    public RandommerNameGenerator(
            @Value("${randommer.uri}") String randommerNameGenerateUri,
            @Value("${randommer.api-key}") String randommerApiKey
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(randommerNameGenerateUri)
                .build();
        this.apiKey = randommerApiKey;
    }

    @Override
    /*
    TODO : 외부 API 예외 상황 처리 추가 필요
    TODO : 한국어로된 이름을 생성할 필요가 있음. -> 외부 API 찾아보고 없으면 구현해야 할듯
    ex) 달콤한 햄스터, 꿋꿋한 춘식이, 놀라운 기린
     */
    public String generate() {
        String result = restClient.get()
                .header("X-Api-Key", apiKey)
                .retrieve()
                .body(String.class);
        System.out.println(result);
        return result.substring(2, result.length() - 2);
    }
}
