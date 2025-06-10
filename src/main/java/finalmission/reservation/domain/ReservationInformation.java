package finalmission.reservation.domain;

import finalmission.restaurant.domain.Restaurant;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(uniqueConstraints =
    @UniqueConstraint(columnNames = {"restaurant_id", "date", "start_at"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    private LocalDate date;

    private LocalTime startAt;

    private Integer maxCount; // 예약 가능한 최대 건수
}
