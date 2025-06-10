package finalmission.reservation.domain;

import finalmission.member.domain.User;
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
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Umbrella umbrella;

    private LocalDate reservationDate;

    private Reservation(Long id, User user, Umbrella umbrella, LocalDate reservationDate) {
        this.id = id;
        this.user = user;
        this.umbrella = umbrella;
        this.reservationDate = reservationDate;
    }

    public static Reservation createWithoutId(User user, Umbrella umbrella, LocalDate reservationDate) {
        return new Reservation(null, user, umbrella, reservationDate);
    }
}
