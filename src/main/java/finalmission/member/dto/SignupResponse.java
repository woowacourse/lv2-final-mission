package finalmission.member.dto;

import finalmission.member.domain.Member;

public record SignupResponse(
        Long id,
        String name,
        String email,
        String password
) {
    public static SignupResponse from(Member member) {
        return new SignupResponse(member.getId(), member.getName(), member.getEmail(), member.getPassword());
    }
}
