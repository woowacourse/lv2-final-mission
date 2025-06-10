package finalmission.reservation.domain;

import finalmission.member.domain.Member;
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

    @Column(nullable = false)
    private LocalDate reservationDate;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Umbrella umbrella;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    private Reservation(Long id, LocalDate reservationDate, Member member, Umbrella umbrella, ReservationStatus status) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.member = member;
        this.umbrella = umbrella;
        this.status = status;
    }

    public static Reservation createPendingWithoutId(final LocalDate reservationDate, final Member member, final Umbrella umbrella){
        return new Reservation(null, reservationDate, member, umbrella,ReservationStatus.PENDING);
    }

    public void changeTopPaid(){
        this.status = ReservationStatus.PAID;
    }
}
