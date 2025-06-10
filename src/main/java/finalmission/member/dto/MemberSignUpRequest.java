package finalmission.member.dto;

public record MemberSignUpRequest(
        String email,
        String name,
        String password
) {
}
