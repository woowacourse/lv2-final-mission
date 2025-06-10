package finalmission.member.dto;

import finalmission.member.domain.Member;

public record MemberRegistrationResponse(long memberId, String name, String email) {
    public static MemberRegistrationResponse from(Member member) {
        return new MemberRegistrationResponse(member.getId(), member.getName(), member.getEmail());
    }
}
