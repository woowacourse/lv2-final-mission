package library.borrow.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import library.collection.domain.Collection;
import library.member.domain.Member;

@Entity
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate dueDate;

    public Borrow(final Collection collection, final Member member, final LocalDate dueDate) {
        this.collection = collection;
        this.member = member;
        this.dueDate = dueDate;
    }

    public Collection getCollection() {
        return collection;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Member getMember() {
        return member;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
