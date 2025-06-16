package finalmission.reservatioin.entity;

import finalmission.customer.entity.Customer;
import finalmission.omakase.entity.Omakase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Omakase omakase;

    @Enumerated
    @Column(nullable = false)
    private ReservationTime reservationTime;

    @Column(nullable = false)
    private LocalDate reservationDate;

    protected Reservation() {
    }

    public Reservation(Customer customer, Omakase omakase, ReservationTime reservationTime, LocalDate reservationDate) {
        this(null, customer, omakase, reservationTime, reservationDate);
    }

    public Reservation(Long id, Customer customer, Omakase omakase, ReservationTime reservationTime,
                       LocalDate reservationDate) {
        this.id = id;
        this.customer = customer;
        this.omakase = omakase;
        this.reservationTime = reservationTime;
        this.reservationDate = reservationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Omakase getOmakase() {
        return omakase;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public boolean isSameCustomerId(Long customerId) {
        return customer.isSameId(customerId);
    }
}
