package finalmission.reservation.domain;

import finalmission.restaurant.domain.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservation_slots")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
public class ReservationSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;   // 낙관적 락을 위한 버전

    @Column(nullable = false)
    private LocalDate date;

    @JoinColumn(name = "time_id", nullable = false)
    @ManyToOne
    private ReservationTime time;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne
    private Restaurant restaurant;

    @Column(nullable = false)
    private int maxReservationCount;

    @Column(nullable = false)
    private int currentReservationCount;

    public ReservationSlot(
            final LocalDate date,
            final ReservationTime time,
            final Restaurant restaurant,
            final int maxReservationCount
    ) {
        validateDate(date);
        validateTime(time);
        validateRestaurant(restaurant);
        validateMaxReservationCount(maxReservationCount);

        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
        this.maxReservationCount = maxReservationCount;
        this.currentReservationCount = 0;
    }

    public boolean isFull() {
        return currentReservationCount >= maxReservationCount;
    }

    public void increaseCurrentReservationCount() {
        if (currentReservationCount >= maxReservationCount) {
            throw new IllegalStateException("최대 예약 수를 초과할 수 없습니다.");
        }
        currentReservationCount++;
    }

    public void decreaseCurrentReservationCount() {
        if (currentReservationCount <= 0) {
            throw new IllegalStateException("현재 예약 수가 0이므로 감소시킬 수 없습니다.");
        }
        currentReservationCount--;
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

    private void validateMaxReservationCount(final int maxReservationCount) {
        if (maxReservationCount < 0) {
            throw new IllegalArgumentException("최대 예약 수는 0 미만일 수 없습니다.");
        }
    }
}
