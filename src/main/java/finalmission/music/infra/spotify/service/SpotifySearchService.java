package finalmission.music.infra.spotify.service;

import finalmission.music.infra.spotify.controller.dto.SpotifyAlbumSearchResponse;
import finalmission.music.infra.spotify.service.client.SpotifyTokenClient;
import finalmission.music.infra.spotify.service.dto.SpotifyAlbums;
import finalmission.music.infra.spotify.service.dto.SpotifyToken;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class SpotifySearchService {

    private final RestClient spotifyRestClient;
    private final SpotifyTokenClient spotifyTokenClient;

    public SpotifyAlbums search(String q) {
        SpotifyToken token = spotifyTokenClient.getToken();

        String uri = "/search?q=" + q + "&type=album&limit=1";
        return Objects.requireNonNull(spotifyRestClient.get()
                .uri(uri)
                .header("Authorization", token.token_type() + " " + token.access_token())
                .retrieve()
                .body(SpotifyAlbumSearchResponse.class))
            .albums();
    }
}
