package finalmission.member.presentation.dto.request;

public record MemberCreateWebRequest(String username, String password, String name) {

    public MemberCreateWebRequest {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("아이디는 빈 값이 될 수 없습니다.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 빈 값이 될 수 없습니다.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 값이 될 수 없습니다.");
        }
    }
}
