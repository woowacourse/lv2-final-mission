package finalmission.music.infra.spotify;

import finalmission.music.infra.spotify.service.SpotifySearchService;
import finalmission.music.infra.spotify.service.client.SpotifyTokenClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@TestConfiguration
public class SpotifyClientTestConfig {

    private final int wireMockPort;
    private final int readTimeout;
    private final int connectTimeout;

    public SpotifyClientTestConfig(@Value("${wiremock.server.port}") int wireMockPort,
                                   @Value("${spotify.timeout.read}") int readTimeout,
                                   @Value("${spotify.timeout.connect}") int connectTimeout) {
        this.wireMockPort = wireMockPort;
        this.readTimeout = readTimeout;
        this.connectTimeout = connectTimeout;
    }

    @Bean
    public RestClient spotifyTokenTestRestClient() {
        return RestClient.builder()
            .baseUrl("http://localhost:"+wireMockPort+"/api/token")
            .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
            .requestFactory(requestFactory())
            .build();
    }

    @Bean
    public RestClient spotifyRestTestClient() {
        return RestClient.builder()
            .baseUrl("http://localhost:"+wireMockPort+"/v1")
            .requestFactory(requestFactory())
            .build();
    }

    private SimpleClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(connectTimeout);
        simpleClientHttpRequestFactory.setReadTimeout(readTimeout);

        return simpleClientHttpRequestFactory;
    }

    @Bean
    public SpotifyTokenClient spotifyTokenClient() {
        return new SpotifyTokenClient("test", "test-key", spotifyTokenTestRestClient());
    }

    @Bean
    public SpotifySearchService spotifySearchService() {
        return new SpotifySearchService(spotifyRestTestClient(), spotifyTokenClient());
    }
}
