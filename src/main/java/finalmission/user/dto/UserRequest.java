package finalmission.user.dto;

public class UserRequest {

    private UserRequest() {
    }

    public record Login(
            String email,
            String password
    ) {

    }

    public record Join(
            String name,
            String email,
            String password
    ) {

    }
}
