package finalmission.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private RoleType role;

    public Member(String name, String nickname, String email, String password, RoleType role) {
        this(null, name, nickname, email, password, role);
    }

    public void promoteToAdmin() {
        this.role = RoleType.ADMIN;
    }

    public boolean matchesPassword(String password) {
        return this.password.equals(password);
    }
}
