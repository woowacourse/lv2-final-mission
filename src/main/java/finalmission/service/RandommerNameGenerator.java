package finalmission.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
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
    일단 현재는 실패시 로깅만 추가
    TODO : 한국어로된 이름을 생성할 필요가 있음. -> 외부 API 찾아보고 없으면 구현해야 할듯
    ex) 달콤한 햄스터, 꿋꿋한 춘식이, 놀라운 기린
     */
    public String generate() {
        List result = restClient.get()
                .header("X-Api-Key", apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        ((req, resp) -> {
                            log.warn("randommer API 호출 중 예외 발생, request={}, response={}", req, resp);
                        })
                )
                .body(List.class);
        return result.getFirst().toString();
    }
}
