package finalmission.dateprice.domain;

import finalmission.accommodation.domain.Accommodation;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class DatePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Accommodation accommodation;

    public DatePrice(long id, LocalDate date, long price, Accommodation accommodation) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.accommodation = accommodation;
    }

    public DatePrice(LocalDate date, long price, Accommodation accommodation) {
        this.date = date;
        this.price = price;
        this.accommodation = accommodation;
    }

    protected DatePrice() {
    }

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getPrice() {
        return price;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }
}
