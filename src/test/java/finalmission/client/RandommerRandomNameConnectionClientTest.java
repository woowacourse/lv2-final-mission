package finalmission.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
class RandommerRandomNameConnectionClientTest {

    @Value("${client.random-name.base-url}")
    private String baseUrl;
    @Value("${client.random-name.secret-key}")
    private String apiKey;
    @Value("${client.random-name.generate_url}")
    private String generationUrl;

    @DisplayName("Randommer에 연결할 수 있다.")
    @Test
    void canCheckConnection() {
        // given
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(2));
        requestFactory.setReadTimeout(Duration.ofSeconds(20));

        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .defaultHeader("Content-Type", "application/json")
                .build();

        RandomNameClient randomNameClient = new RandommerRandomNameClient(restClient, apiKey, generationUrl);

        // when
        List<String> names = randomNameClient.generateRandomNames(1);

        // then
        assertThat(names).hasSize(1);
    }

}
