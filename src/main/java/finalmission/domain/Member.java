package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Member {

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    protected Member() {
    }

    public Member(String randomName) {
        this.nickname = randomName;
        this.password = randomName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
