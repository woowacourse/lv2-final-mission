package finalmission.member.dto;

import finalmission.member.domain.Member;

public record MemberResponse(long memberId, String name) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName());
    }
}
