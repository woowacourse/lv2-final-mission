package finalmission.music.lottery.controller.dto;

import finalmission.music.album.controller.dto.AlbumResponse;
import finalmission.music.album.domain.Album;
import finalmission.music.lottery.domain.Lottery;
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
