package finalmission.stallstatus.controller;

import finalmission.stallstatus.entity.StallStatus;
import finalmission.stallstatus.entity.Status;

import java.time.LocalDateTime;

public record MyStallStatusResponse(
        Long stallStatusId,
        String randomNickName,
        LocalDateTime createAt,
        String stallName,
        Status status
) {
    public static MyStallStatusResponse from(StallStatus stallStatus) {
        return new MyStallStatusResponse(
                stallStatus.getId(),
                stallStatus.getRandomNickname(),
                stallStatus.getCreateAt(),
                stallStatus.getStall().getName(),
                stallStatus.getStatus()
        );
    }
}
