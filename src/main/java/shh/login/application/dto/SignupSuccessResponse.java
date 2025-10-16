package shh.login.application.dto;

import shh.member.domain.Member;

public record SignupSuccessResponse(
        String name
) {
    public static SignupSuccessResponse from(final Member member) {
        return new SignupSuccessResponse(member.getName().getValue());
    }
}
