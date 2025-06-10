package finalmission.member.ui.dto;

import finalmission.member.domain.Member;

public record MemberResponse(
        Long id,
        String nickname,
        String email

) {

    public static MemberResponse from(final Member member) {
        return new MemberResponse(
                member.getId(),
                member.getNickname(),
                member.getEmail()
        );
    }
}
