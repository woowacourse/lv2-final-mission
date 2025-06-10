package finalmission.domain;

public class Nickname {
    private final String nickname;

    public Nickname(String nickname) {
        validateLength(nickname);
        this.nickname = nickname;
    }

    private void validateLength(String nickname) {
        if (nickname.length() < 2 || nickname.length() > 4) {
            throw new IllegalArgumentException("크루 닉네임은 최소 2글자, 최대 4글자여야합니다.");
        }
    }
}
