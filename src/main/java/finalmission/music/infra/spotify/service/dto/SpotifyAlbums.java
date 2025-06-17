package finalmission.music.infra.spotify.service.dto;

public record SpotifyAlbums(
    SimplifiedAlbumObject[] items
) {
}
