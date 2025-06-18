package finalmission.member.domain;

import finalmission.common.exception.InvalidInputException;
import finalmission.member.domain.utils.RoleConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Member {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Convert(converter = RoleConverter.class)
    @Column(nullable = false)
    private Role role;

    public Member(final String name, final String email, final String password) {
        validate(name, email, password);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.MEMBER;
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    private void validate(
            final String name,
            final String email,
            final String password
    ) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("이름은 null이거나 빈 값일 수 없습니다.");
        }
        if (email == null || email.isBlank()) {
            throw new InvalidInputException("이메일은 null이거나 빈 값일 수 없습니다.");
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new InvalidInputException("이메일 형식이 올바르지 않습니다.");
        }
        if (password == null || password.isBlank()) {
            throw new InvalidInputException("비밀번호는 null이거나 빈 값일 수 없습니다.");
        }
    }
}
