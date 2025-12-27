package library.collection.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import library.book.domain.Book;
import library.borrow.domain.Borrow;
import library.reservation.domain.Reservation;


//- 청구기호,	소장처/자료실,	도서상태,	반납예정일
@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;

    @Enumerated(EnumType.STRING)
    private CollectionStatus collectionStatus;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "collection")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToOne(mappedBy = "collection")
    private Borrow borrow;

    public Collection() {
    }

    public Book getBook() {
        return book;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public CollectionStatus getCollectionStatus() {
        return collectionStatus;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Borrow getBorrow() {
        return borrow;
    }

    public void setCollectionStatus(final CollectionStatus collectionStatus) {
        this.collectionStatus = collectionStatus;
    }
}
