package finalmission.music.infra.spotify.controller.dto;

import finalmission.music.infra.spotify.service.dto.SpotifyAlbums;

public record SpotifyAlbumSearchResponse(
    SpotifyAlbums albums
) {
}
