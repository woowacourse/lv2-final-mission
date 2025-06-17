package finalmission.music.lottery.controller.dto;

import java.time.LocalDateTime;

public record LotteryRequest(Long albumId, LocalDateTime expirationDateTime) {
}
