package finalmission.reservation.domain;

import finalmission.restaurant.domain.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationSlot {

    @Column(nullable = false)
    private LocalDate date;

    @JoinColumn(name = "time_id", nullable = false)
    @ManyToOne
    private ReservationTime time;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne
    private Restaurant restaurant;

    public ReservationSlot(
            final LocalDate date,
            final ReservationTime time,
            final Restaurant restaurant
    ) {
        validateDate(date);
        validateTime(time);
        validateRestaurant(restaurant);

        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
    }

    private void validateDate(final LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("날짜는 null이면 안됩니다.");
        }
    }

    private void validateTime(final ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException("시간은 null이면 안됩니다.");
        }
    }

    private void validateRestaurant(final Restaurant restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("음식점은 null이면 안됩니다.");
        }
    }
}
