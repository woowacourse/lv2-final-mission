package finalmission.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

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
    TODO : 한국어로된 이름을 생성할 필요가 있음. -> 외부 API 찾아보고 없으면 구현해야 할듯
    ex) 달콤한 햄스터, 꿋꿋한 춘식이, 놀라운 기린
     */
    public String generate() {
        try {
            return generateByRandommerApi();
        } catch (Exception e) {
            return generateByUuid();
        }
    }

    private String generateByRandommerApi() {
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

    private String generateByUuid() {
        String randomUuid = UUID.randomUUID().toString().substring(0, 8);
        return "투표자%s".formatted(randomUuid);
    }
}
