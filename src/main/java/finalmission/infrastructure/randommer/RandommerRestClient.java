package finalmission.infrastructure.randommer;

import finalmission.dto.NameGenerateRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
public class RandommerRestClient {

    @Value("${name-generator.api.key}")
    private String apiKey;

    private final RestClient restClient;

    public String generateSingleName() {
        return restClient.get()
                .uri("?nameType=firstname&quantity=1")
                .accept(MediaType.ALL)
                .header("X-Api-Key", apiKey)
                .retrieve()
                .body(new ParameterizedTypeReference<String>() {
                });
    }

    public List<String> generateNames(NameGenerateRequestDto nameGenerateRequestDto) {
        return restClient.get()
                .uri("?nameType=firstname&quantity=" + nameGenerateRequestDto.quantity())
                .accept(MediaType.ALL)
                .header("X-Api-Key", apiKey)
                .retrieve()
                .body(new ParameterizedTypeReference<List<String>>() {
                });
    }
}
