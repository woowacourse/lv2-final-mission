package finalmission.member.controller.dto.response;

import finalmission.member.domain.Member;

public record MemberResponse(long id, String email, String name)
{
    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getName());
    }
}
