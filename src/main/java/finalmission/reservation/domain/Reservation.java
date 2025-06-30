package finalmission.reservation.domain;

import java.time.LocalDate;
import finalmission.member.domain.Member;
import finalmission.reservation.Seat;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.station.domain.Station;
import finalmission.subway.domain.Subway;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Subway subway;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Seat seat;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Station departStation;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Station arriveStation;

    public boolean isOwner(Member member) {
        return this.member.equals(member);
    }

    public void updateReservation(LocalDate date, Subway subway, Seat seat, Station departStation, Station arriveStation) {
        this.date = date;
        this.subway = subway;
        this.seat = seat;
        this.departStation = departStation;
        this.arriveStation = arriveStation;
    }
}
