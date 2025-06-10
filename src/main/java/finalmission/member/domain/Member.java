package finalmission.member.domain;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;


    protected Member() {}

    public Member(final String email, final String password, final String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = Role.USER;
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

    public Role getRole() {
        return role;
    }
}
