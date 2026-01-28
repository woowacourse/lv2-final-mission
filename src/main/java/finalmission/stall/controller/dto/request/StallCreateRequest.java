package finalmission.stall.controller.dto.request;

import finalmission.stall.entity.Stall;

public record StallCreateRequest(String name) {
    public Stall toStall() {
        return new Stall(name);
    }
}
