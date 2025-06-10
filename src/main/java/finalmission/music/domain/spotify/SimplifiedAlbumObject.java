package finalmission.music.domain.spotify;

public record SimplifiedAlbumObject(
    String album_type,
    int total_tracks,
    String id,
    String name,
    String release_date,
    SimplifiedArtistObject[] artists
) {
}
