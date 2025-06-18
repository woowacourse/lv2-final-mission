package finalmission.controller.dto;

import finalmission.domain.Member;
import finalmission.domain.vo.LolName;

public record MemberSignUpRequest(
        LolName lolName,
        String password
) {

    public Member toMember() {
        return new Member(lolName, password);
    }
}
