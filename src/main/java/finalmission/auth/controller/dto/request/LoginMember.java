package finalmission.auth.controller.dto.request;

public record LoginMember(
        Long id,
        String email,
        String password
) {
}
