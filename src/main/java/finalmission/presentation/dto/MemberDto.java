package finalmission.presentation.dto;

import finalmission.domain.Member;

public record MemberDto(long id, String name) {

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(), member.getName());
    }
}
