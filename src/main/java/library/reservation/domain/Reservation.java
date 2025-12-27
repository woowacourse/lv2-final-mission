package library.reservation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import library.collection.domain.Collection;
import library.member.domain.Member;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    public Reservation(final Collection collection, final Member member) {
        this.member = member;
        this.collection = collection;
    }

    public Reservation() {

    }

    public Collection getCollection() {
        return collection;
    }

    public Member getMember() {
        return member;
    }

    public Long getId() {
        return id;
    }
}
