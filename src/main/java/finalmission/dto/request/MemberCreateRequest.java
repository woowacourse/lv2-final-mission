package finalmission.dto.request;

public record MemberCreateRequest(
        String name,
        String email,
        String password
) {
}
