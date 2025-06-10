package finalmission.controller.dto;

import finalmission.domain.vo.LolName;
import finalmission.domain.Member;

public record MemberLoginResponse(
        Long id,
        LolName lolName
) {

    public static MemberLoginResponse from(final Member member) {
        return new MemberLoginResponse(member.getId(), member.getLolName());
    }
}
