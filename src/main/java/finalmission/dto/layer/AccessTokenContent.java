package finalmission.dto.layer;

import finalmission.domain.Role;

public record AccessTokenContent(
        Long memberId,
        Role role
) {

}
