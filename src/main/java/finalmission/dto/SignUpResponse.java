package finalmission.dto;

import finalmission.domain.Member;

public record SignUpResponse(
        String email
) {
    public static SignUpResponse from(final Member member) {
        return new SignUpResponse(member.getEmail());
    }
}
