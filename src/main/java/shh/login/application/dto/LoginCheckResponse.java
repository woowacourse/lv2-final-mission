package shh.login.application.dto;

import shh.member.domain.Member;

public record LoginCheckResponse(
        String name
) {
    public static LoginCheckResponse from(final Member member) {
        return new LoginCheckResponse(member.getName().getValue());
    }
}
