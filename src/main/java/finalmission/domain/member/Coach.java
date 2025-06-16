package finalmission.domain.member;

import finalmission.domain.login.MemberType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coach implements Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    protected Coach() {}

    public Coach(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Coach(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public MemberType getType() {
        return MemberType.COACH;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
