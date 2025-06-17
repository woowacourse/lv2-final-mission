package finalmission.music.controller.dto;

import finalmission.music.domain.Album;
import finalmission.music.domain.Lottery;
import java.time.LocalDateTime;

public record LotteryResponse(AlbumResponse albumResponse, LocalDateTime expirationDateTime) {

    public static LotteryResponse from(final Lottery lottery) {
        Album album = lottery.getAlbum();
        return new LotteryResponse(
            new AlbumResponse(album.getName(), album.getArtistName(), album.getTotalTracks(), album.getReleaseDate()),
            lottery.getExpirationDateTime()
        );
    }
}
