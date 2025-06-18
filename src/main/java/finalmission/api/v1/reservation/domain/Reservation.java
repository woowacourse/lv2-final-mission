package finalmission.api.v1.reservation.domain;


import finalmission.api.v1.restaurant.domain.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String nickname;

    @JoinColumn(name = "restaurant_time_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ReservationTime reservationTime;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String phoneNumber;

    @Builder
    public Reservation(final Long id, final LocalDate date, final String nickname,
                       final ReservationTime reservationTime, final Restaurant restaurant,
                       final String phoneNumber) {
        this.id = id;
        this.date = date;
        this.nickname = nickname;
        this.reservationTime = reservationTime;
        this.restaurant = restaurant;
        this.phoneNumber = phoneNumber;
    }

    public boolean isSamePhoneNumber(final String phoneNumber) {
        return this.phoneNumber.equals(phoneNumber);
    }

    public void modifyPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

