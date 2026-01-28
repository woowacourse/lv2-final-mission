package finalmission.stall.controller.dto.response;

import finalmission.stall.entity.Stall;

public record StallCreateResponse(Long id, String name) {
    public static StallCreateResponse from(Stall stall) {
        return new StallCreateResponse(stall.getId(), stall.getName());
    }
}
