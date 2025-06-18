package finalmission.infra.thirdparty;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.infra.thirdparty.dto.RestDayRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
class RestDayRestClientTest {

    @Autowired
    RestDayRestClient restDayRestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${data.base-url}")
    private String baseUrl;

    @Value("${data.api-key}")
    private String apiKey;

    @Test
    void 공공데이터_포털_연결_응답_테스트() {
        // given
        RestDayRequest restDayRequest = new RestDayRequest(2025, 5, 25);

        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("ServiceKey", apiKey)
                .queryParam("solYear", 2025)
                .queryParam("solMonth", String.format("%02d", 5))
                .queryParam("_type", "json")
                .build(true)
                .toUriString();
        System.out.println("Generated URI: " + uri);

        // when
        ResponseEntity<String> response = restDayRestClient.getRestDay(restDayRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }
}
