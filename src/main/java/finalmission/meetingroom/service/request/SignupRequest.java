package finalmission.meetingroom.service.request;

public record SignupRequest(
        String name,
        String email,
        String password
) {
}
