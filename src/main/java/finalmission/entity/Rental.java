package finalmission.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private LocalDate rentalDate;
    private LocalDate returnDate;

    public Rental() {
    }

    public Rental(Long id, Member member, Book book, LocalDate rentalDate, LocalDate returnDate) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Rental(Member member, Book book, LocalDate rentalDate, LocalDate returnDate) {
        this.member = member;
        this.book = book;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
