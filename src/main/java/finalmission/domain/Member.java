package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Member {

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
