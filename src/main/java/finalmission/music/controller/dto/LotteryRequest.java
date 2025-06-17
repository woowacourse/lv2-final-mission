package finalmission.music.controller.dto;

import java.time.LocalDateTime;

public record LotteryRequest(Long albumId, LocalDateTime expirationDateTime) {
}
