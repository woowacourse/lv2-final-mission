package finalmission.music.infra.spotify.service;

import finalmission.music.infra.spotify.controller.dto.SpotifyAlbumSearchResponse;
import finalmission.music.infra.spotify.service.dto.SpotifyAlbums;
import finalmission.music.infra.spotify.service.dto.SpotifyToken;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SpotifySearchService {

    @Value("${spotify.client.id}")
    private String clientId;
    @Value("${spotify.client.secret.key}")
    private String clientSecretKey;

    public SpotifyToken getToken() {
        RestClient restClient = RestClient.create();

        return restClient.post()
            .uri("https://accounts.spotify.com/api/token")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .body("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecretKey)
            .retrieve()
            .body(SpotifyToken.class);
    }

    public SpotifyAlbums search(String q) {
        SpotifyToken token = getToken();

        RestClient restClient = RestClient.create();

        String uri = "https://api.spotify.com/v1/search?q=" + q + "&type=album&limit=1";
        return Objects.requireNonNull(restClient.get()
                .uri(uri)
                .header("Authorization", token.token_type() + " " + token.access_token())
                .retrieve()
                .body(SpotifyAlbumSearchResponse.class))
            .albums();
    }
}
