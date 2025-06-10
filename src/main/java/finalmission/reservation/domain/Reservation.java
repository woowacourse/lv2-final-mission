package finalmission.reservation.domain;

import finalmission.station.domain.Station;
import finalmission.subway.domain.Subway;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Subway subway;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Station departStation;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Station arriveStation;
}
