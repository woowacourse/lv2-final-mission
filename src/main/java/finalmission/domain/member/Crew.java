package finalmission.domain.member;

import finalmission.domain.login.MemberType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Crew implements Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    protected Crew() {}

    public Crew(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Crew(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public MemberType getType() {
        return MemberType.CREW;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
