package finalmission.controller.dto;

import finalmission.domain.vo.LolName;
import finalmission.domain.Member;

public record MemberSignUpRequest(
        LolName lolName,
        String password
) {

    public Member toMember() {
        return new Member(lolName, password);
    }
}
