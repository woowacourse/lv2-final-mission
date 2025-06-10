package finalmission.controller;

import finalmission.domain.LolName;
import finalmission.domain.Member;

public record MemberSignUpRequest(
        LolName lolName,
        String password
) {

    public Member toMember() {
        return new Member(lolName, password);
    }
}
