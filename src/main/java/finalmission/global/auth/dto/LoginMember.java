package finalmission.global.auth.dto;

import finalmission.member.entity.RoleType;

public record LoginMember(
        Long id,
        String name,
        RoleType role
) {
}
