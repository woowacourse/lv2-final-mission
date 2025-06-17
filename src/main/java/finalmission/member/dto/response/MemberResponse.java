package finalmission.member.dto.response;

import finalmission.member.entity.Member;
import finalmission.member.entity.RoleType;

public record MemberResponse(
        Long id,
        String name,
        String email,
        RoleType role
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getRole()
        );
    }
}
