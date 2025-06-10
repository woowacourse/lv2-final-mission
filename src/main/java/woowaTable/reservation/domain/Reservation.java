package woowaTable.reservation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowaTable.restaurant.domain.Restaurant;
import woowaTable.user.domain.Customer;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @OneToOne
    private Restaurant restaurant;

    @ManyToOne
    private Customer customer;

    private Reservation(
            final Long id,
            final LocalDateTime dateTime,
            final Restaurant restaurant,
            final Customer customer
    ) {
        this.id = id;
        this.dateTime = dateTime;
        this.restaurant = restaurant;
        this.customer = customer;
    }

    public Reservation(
            final LocalDateTime dateTime,
            final Restaurant restaurant,
            final Customer customer
    ) {
        this(null, dateTime, restaurant, customer);
    }
}
