package finalmission.controller.dto;

import finalmission.domain.Member;

public record MemberSignupRequest(
        String nickname,
        String email,
        String password,
        String phoneNumber
) {

    public Member toMember() {
        return new Member(
                nickname,
                email,
                password,
                phoneNumber
        );
    }
}
