package finalmission.music.infra.spotify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class SpotifyRestClientConfig {

    private final int readTimeout;
    private final int connectionTimeout;

    public SpotifyRestClientConfig(@Value("${spotify.timeout.read}") int readTimeout,
                                   @Value("${spotify.timeout.connection}") int connectionTimeout) {
        this.readTimeout = readTimeout;
        this.connectionTimeout = connectionTimeout;
    }

    @Bean
    public RestClient spotifyTokenRestClient() {
        return RestClient.builder()
            .baseUrl("https://accounts.spotify.com/api/token")
            .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
            .requestFactory(requestFactory())
            .build();
    }

    @Bean
    public RestClient spotifyRestClient() {
        return RestClient.builder()
            .baseUrl("https://api.spotify.com/v1")
            .requestFactory(requestFactory())
            .build();
    }

    private SimpleClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(connectionTimeout);
        simpleClientHttpRequestFactory.setReadTimeout(readTimeout);

        return simpleClientHttpRequestFactory;
    }
}
