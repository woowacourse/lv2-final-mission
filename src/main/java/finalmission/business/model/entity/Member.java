package finalmission.business.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;

    public Member() {
    }

    public static Member create(String name, String email, String password) {
        return new Member(null, name, email, password);
    }

    public static Member create(Long id, String name, String email, String password) {
        return new Member(id, name, email, password);
    }
}
