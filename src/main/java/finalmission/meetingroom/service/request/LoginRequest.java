package finalmission.meetingroom.service.request;

public record LoginRequest(
        String email,
        String password
) {
}
