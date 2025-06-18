package finalmission.dto.response;

import finalmission.domain.Member;
import finalmission.domain.MemberRole;

public record MemberResponse(
        Long id,
        String email,
        String name,
        MemberRole memberRole
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getName(), member.getMemberRole());
    }
}
