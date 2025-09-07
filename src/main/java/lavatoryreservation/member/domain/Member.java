package lavatoryreservation.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lavatoryreservation.lavatory.domain.Sex;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private Email email;
    private Sex sex;

    public Member(Long id, String name, String email, Sex sex) {
        this.id = id;
        this.name = name;
        this.email = new Email(email);
        this.sex = sex;
    }

    protected Member() {

    }

    public String getEmail() {
        return email.getEmailString();
    }

    public Long getId() {
        return id;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public boolean isSameMember(Member member) {
        return email.isSameMember(member.email);
    }
}
