package finalmission.member.dto;

public record SignupRequest(boolean wantRandomNickname, String nickname, String email, String password) {
}
