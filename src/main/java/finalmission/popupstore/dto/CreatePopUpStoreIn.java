package finalmission.popupstore.dto;

import java.time.LocalDateTime;

public record CreatePopUpStoreIn(
        String title,
        String content,
        LocalDateTime startAt,
        LocalDateTime endAt,
        int maxEnteredMemberCount,
        Long shopkeeperId
) {
}
