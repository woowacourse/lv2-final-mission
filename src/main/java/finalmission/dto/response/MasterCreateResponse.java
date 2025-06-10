package finalmission.dto.response;

import finalmission.domain.Master;

public record MasterCreateResponse(
        Long id,
        String email,
        String name
) {

    public static MasterCreateResponse from(Master master) {
        return new MasterCreateResponse(master.getId(), master.getEmail(), master.getName());
    }
}
