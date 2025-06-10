package finalmission.music.controller.dto;

import finalmission.music.domain.Album;
import java.time.LocalDate;

public record AlbumResponse(
    String name,
    String artistName,
    int totalTracks,
    LocalDate releaseDate) {

    public static AlbumResponse from(final Album album) {
        return new AlbumResponse(album.getName(), album.getArtistName(), album.getTotalTracks(), album.getReleaseDate());
    }
}
