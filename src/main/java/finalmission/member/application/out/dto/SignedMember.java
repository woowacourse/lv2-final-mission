package finalmission.member.application.out.dto;

import finalmission.member.domain.Member;

public record SignedMember(
        Long id,
        String name,
        String nickname
) {
    public static SignedMember from(Member member) {
        return new SignedMember(
                member.getId(),
                member.getName(),
                member.getNickname()
        );
    }
}
