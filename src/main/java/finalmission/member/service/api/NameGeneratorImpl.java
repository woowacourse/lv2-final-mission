package finalmission.member.service.api;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NameGeneratorImpl implements NameGenerator {

    private final RestClient nameGeneratorRestClient;

    @Override
    public String generate() {
        return Objects.requireNonNull(nameGeneratorRestClient.get()
                .uri(uriBuilder -> uriBuilder.path("")
                        .queryParam("nameType", "surname")
                        .queryParam("quantity", 1)
                        .build())
                .retrieve()
                .body(String[].class))[0];
    }
}
