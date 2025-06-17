package finalmission.music.service;

import finalmission.music.controller.dto.LotteryResponse;
import finalmission.music.domain.Album;
import finalmission.music.domain.Lottery;
import finalmission.music.repository.AlbumRepository;
import finalmission.music.repository.LotteryRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LotteryService {

    private final AlbumRepository albumRepository;
    private final LotteryRepository lotteryRepository;

    public LotteryResponse createLottery(final Long albumId, final LocalDateTime expirationDateTime) {
        Album album = albumRepository.findById(albumId)
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 앨범입니다."));
        validateExpirationDateTime(expirationDateTime);
        Lottery lottery = new Lottery(album, expirationDateTime);

        return LotteryResponse.from(lotteryRepository.save(lottery));
    }

    private void validateExpirationDateTime(LocalDateTime expirationDateTime) {
        if (LocalDateTime.now().isAfter(expirationDateTime)) {
            throw new IllegalArgumentException("[ERROR] 마감 기한이 잘못되었습니다.");
        }
    }
}
