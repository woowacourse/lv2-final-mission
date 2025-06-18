package finalmission.auth.dto;

import finalmission.member.domain.Member;

public record LoginMember(
        Long id,
        String email
) {

    public static LoginMember from(Member member) {
        return new LoginMember(member.getId(), member.getEmail());
    }
}
