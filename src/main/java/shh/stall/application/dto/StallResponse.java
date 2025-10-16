package shh.stall.application.dto;

import shh.stall.domain.Stall;

public record StallResponse(
        Long id,
        String name
) {
    public static StallResponse from(final Stall stall) {
        return new StallResponse(stall.getId(), stall.getName());
    }
}
