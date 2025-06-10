package finalmission.popupstore.application.in.dto;

import java.time.LocalDateTime;

public record CreatePopUpStore(
        String title,
        String content,
        LocalDateTime startAt,
        LocalDateTime endAt,
        int maxEnteredMemberCount,
        Long shopkeeperId
) {
}
