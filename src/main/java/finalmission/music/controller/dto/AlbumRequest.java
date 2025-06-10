package finalmission.music.controller.dto;

import java.time.LocalDate;

public record AlbumRequest(
    String name,
    String artistName,
    int totalTracks,
    LocalDate releaseDate,
    String spotifyId
) {
}
