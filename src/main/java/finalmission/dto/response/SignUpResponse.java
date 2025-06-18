package finalmission.dto.response;

import finalmission.domain.Member;

public record SignUpResponse(String email, String name) {

    public static SignUpResponse from(Member member) {
        return new SignUpResponse(member.getEmail(), member.getName());
    }
}
