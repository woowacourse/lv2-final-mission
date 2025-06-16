package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Member member;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Education education;

    protected Coach() {
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Education getEducation() {
        return education;
    }
}
