package finalmission.member.dto;

import finalmission.member.domain.Member;

public record MemberCreateResponse(
        Long id,
        String name,
        String email
) {
    public static MemberCreateResponse from(Member member) {
        return new MemberCreateResponse(member.getId(), member.getName(), member.getEmail());
    }
}
