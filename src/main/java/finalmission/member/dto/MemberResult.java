package finalmission.member.dto;

import finalmission.member.domain.Member;

public record MemberResult(
        Long id,
        String name,
        String email
) {

    public static MemberResult toResult(Member member) {
        return new MemberResult(member.getId(), member.getName(), member.getPassword());
    }
}
