package finalmission.dto.request;

import finalmission.common.Role;

public record LoginMemberRequest(
        Long id,
        String name,
        Role role
) {
}
