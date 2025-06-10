package finalmission.infra.auth;

import finalmission.domain.MemberRole;

public record LoginMember(
        Long id,
        String email,
        String name,
        MemberRole memberRole
) {
}
