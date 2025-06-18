package finalmission.reservation.domain;

import finalmission.customer.domain.Customer;
import finalmission.umbrella.domain.Umbrella;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    private Umbrella umbrella;

    private LocalDate reservationDate;

    private Reservation(Long id, Customer customer, Umbrella umbrella, LocalDate reservationDate) {
        this.id = id;
        this.customer = customer;
        this.umbrella = umbrella;
        this.reservationDate = reservationDate;
    }

    public static Reservation createWithoutId(Customer customer, Umbrella umbrella, LocalDate reservationDate) {
        return new Reservation(null, customer, umbrella, reservationDate);
    }
}
