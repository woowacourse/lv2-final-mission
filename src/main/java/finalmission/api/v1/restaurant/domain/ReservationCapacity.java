package finalmission.api.v1.restaurant.domain;


import finalmission.api.v1.reservation.domain.ReservationTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name ="reservation_time", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ReservationTime reservationTime;

    @Column(nullable = false)
    private Long numberOfPeople;
}
