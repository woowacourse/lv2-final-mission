package finalmission.music.controller.dto;

import finalmission.music.domain.spotify.SpotifyAlbums;

public record SpotifyAlbumSearchResponse(
    SpotifyAlbums albums
) {
}
