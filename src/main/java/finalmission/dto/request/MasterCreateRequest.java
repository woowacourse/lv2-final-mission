package finalmission.dto.request;

public record MasterCreateRequest(
        String name,
        String email,
        String password
) {
}
