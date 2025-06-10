package finalmission.member.domain;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;
    @Column(name = "name")
    String name;

    protected Member() {
    }

    public Member(final String email, final String password, final String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static Member beforeSave(
            final String email,
            final String password,
            final String name
    ) {
        return new Member(email, password, name);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
