package finalmission.member.auth.vo;

import finalmission.member.domain.Role;

public record MemberInfo(
        Long id,
        String email,
        String name,
        Role role
) {
}
