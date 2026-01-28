package finalmission.stallstatus.controller.dto.response;

import finalmission.stallstatus.entity.StallStatus;
import finalmission.stallstatus.entity.Status;

import java.time.LocalDateTime;

public record StallStatusCreateResponse(Long stallStatusId, String randomNickName, LocalDateTime createAt,
                                        Status status) {
    public static StallStatusCreateResponse from(StallStatus status) {
        return new StallStatusCreateResponse(status.getId(), status.getRandomNickname(), status.getCreateAt(), status.getStatus());
    }
}
