package finalmission.music.controller;

import finalmission.music.controller.dto.SpotifySearchRequest;
import finalmission.music.controller.dto.SpotifyAlbumSearchResponse;
import finalmission.music.service.SpotifySearchService;
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
