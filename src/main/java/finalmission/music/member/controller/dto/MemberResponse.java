package finalmission.music.member.controller.dto;

import finalmission.music.member.domain.Member;

public record MemberResponse(String name) {

    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getName());
    }
}
