package finalmission.music.infra.spotify.service.client;

import finalmission.music.infra.spotify.service.dto.SpotifyToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SpotifyTokenClient {

    private final String clientId;
    private final String clientSecretKey;
    private final RestClient spotifyTokenRestClient;

    public SpotifyTokenClient(@Value("${spotify.client.id}") String clientId,
                              @Value("${spotify.client.secret.key}") String clientSecretKey,
                              RestClient spotifyTokenRestClient) {
        this.clientId = clientId;
        this.clientSecretKey = clientSecretKey;
        this.spotifyTokenRestClient = spotifyTokenRestClient;
    }

    public SpotifyToken getToken() {
        return spotifyTokenRestClient.post()
            .body("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecretKey)
            .retrieve()
            .body(SpotifyToken.class);
    }
}
