package finalmission.dto.request;

import finalmission.Role;

public record LoginMemberRequest(
        Long id,
        String name,
        Role role
) {
}
