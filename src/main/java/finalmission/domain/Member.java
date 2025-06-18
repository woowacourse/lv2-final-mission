package finalmission.domain;

import finalmission.exception.LoginFailedException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public Member(final Long id, final String name, final String email, final String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Member() {
    }

    public boolean isSameMember(Long memberId) {
        return this.id.equals(memberId);
    }

    public void validatePassword(String password) {
        if (!this.password.equals(password)) {
            throw new LoginFailedException();
        }
    }
}
