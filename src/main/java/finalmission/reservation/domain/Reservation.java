package finalmission.reservation.domain;

import java.time.LocalDate;
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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
