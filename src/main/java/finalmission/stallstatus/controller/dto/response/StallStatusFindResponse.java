package finalmission.stallstatus.controller.dto.response;

import finalmission.stallstatus.entity.StallStatus;
import finalmission.stallstatus.entity.Status;

import java.time.LocalDateTime;

public record StallStatusFindResponse(
        Long stallStatusId,
        String randomNickName,
        LocalDateTime createAt,
        String stallName,
        Status status
) {
    public static StallStatusFindResponse from(StallStatus stallStatus) {
        return new StallStatusFindResponse(
                stallStatus.getId(),
                stallStatus.getRandomNickname(),
                stallStatus.getCreateAt(),
                stallStatus.getStall().getName(),
                stallStatus.getStatus()
        );
    }
}
