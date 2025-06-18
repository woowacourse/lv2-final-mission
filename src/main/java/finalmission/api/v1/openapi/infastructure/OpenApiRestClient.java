package finalmission.api.v1.openapi.infastructure;

import finalmission.api.v1.openapi.NicknameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenApiRestClient {

    private final RestClient restClient;

    public NicknameResponse getName() {
        final String secretKey = "a5590a941dc94d8eb7ccb92065403da7";
        final String url = "https://randommer.io/api/Name?nameType=firstname&quantity=1";

        return restClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Api-Key", secretKey)
                .retrieve()
                .body(NicknameResponse.class);
    }
}
