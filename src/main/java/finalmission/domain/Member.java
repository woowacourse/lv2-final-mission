package finalmission.domain;

import finalmission.exception.MemberException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    public Member() {
    }

    public Member(Long id, String name, String email, String password) {
        validate(name, email, password);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    private void validate(String name, String email, String password) {
        validateName(name);
        validateEmail(email);
        validatePassword(password);
    }

    private void validatePassword(String password) {
        if (password.isBlank()) {
            throw new MemberException("빈 패스워드로 멤버를 생성할 수 없습니다.");
        }
    }

    private void validateEmail(String email) {
        if (email.isBlank()) {
            throw new MemberException("빈 이메일로 멤버를 생성할 수 없습니다.");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() ) {
            throw new MemberException("이름 없이는 멤버를 생성할 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
