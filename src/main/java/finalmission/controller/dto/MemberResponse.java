package finalmission.controller.dto;

import finalmission.domain.Member;

public record MemberResponse(
        Long id,
        String nickname
) {
    public MemberResponse(Member member) {
        this(
                member.getId(),
                member.getNickname()
        );
    }
}
