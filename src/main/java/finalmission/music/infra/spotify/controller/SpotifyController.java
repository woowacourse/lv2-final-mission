package finalmission.music.infra.spotify.controller;

import finalmission.music.infra.spotify.controller.dto.SpotifySearchRequest;
import finalmission.music.infra.spotify.controller.dto.SpotifyAlbumSearchResponse;
import finalmission.music.infra.spotify.service.SpotifySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifySearchService spotifySearchService;

    @PostMapping("/spotify/search")
    public ResponseEntity<SpotifyAlbumSearchResponse> search(@RequestBody final SpotifySearchRequest request) {
        SpotifyAlbumSearchResponse response = new SpotifyAlbumSearchResponse(spotifySearchService.search(request.name()));

        return ResponseEntity.ok(response);
    }
}
