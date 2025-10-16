package finalmission.music.infra.spotify.controller;

import finalmission.music.infra.spotify.controller.dto.SpotifyAlbumSearchResponse;
import finalmission.music.infra.spotify.controller.dto.SpotifySearchRequest;
import finalmission.music.infra.spotify.service.SpotifySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifySearchService spotifySearchService;

    @GetMapping("/spotify/search")
    public ResponseEntity<SpotifyAlbumSearchResponse> search(final String albumName) {
        SpotifyAlbumSearchResponse response = new SpotifyAlbumSearchResponse(spotifySearchService.search(albumName));

        return ResponseEntity.ok(response);
    }
}
