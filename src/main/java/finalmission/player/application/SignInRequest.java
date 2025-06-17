package finalmission.player.application;

public record SignInRequest(
        String nickname,
        String password,
        String email
) {

}
