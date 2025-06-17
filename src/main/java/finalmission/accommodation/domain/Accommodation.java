package finalmission.accommodation.domain;

import finalmission.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Accommodation(long id, String name, String description, String address, Member member) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.member = member;
    }

    public Accommodation(String name, String description, String address, Member member) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.member = member;
    }

    protected Accommodation() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public Member getUser() {
        return member;
    }
}
