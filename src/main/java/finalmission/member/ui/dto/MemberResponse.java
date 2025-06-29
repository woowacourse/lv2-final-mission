package finalmission.member.ui.dto;

import finalmission.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberResponse(
        Long id,
        String nickname,
        String email

) {

    public static MemberResponse from(final Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }
}
