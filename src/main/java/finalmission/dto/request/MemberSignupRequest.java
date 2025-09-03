package finalmission.dto.request;

import finalmission.domain.Role;

public record MemberSignupRequest(
        String name,
        String email,
        String password,
        Role role
) {
}
