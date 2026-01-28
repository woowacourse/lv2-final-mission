package finalmission.user.dto;

public class UserResponse {

    private UserResponse() {
    }

    public record Login(
            String token
    ) {

    }
}
