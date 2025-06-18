package finalmission.controller.dto;

public record MemberLoginRequest(
        String email,
        String password
) {
}
