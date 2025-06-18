package finalmission.infrastructure;

import finalmission.domain.Role;

public record JwtPayload(
        Long memberId,
        String name,
        Role role
) {
}
