package finalmission.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private Seat seat;

    private LocalDateTime registeredAt;

    private LocalDate reservationDate;

    private LocalTime startAt;

    private LocalTime endAt;

    public Reservation(Member member, Seat seat, LocalDate reservationDate, LocalTime startAt, LocalTime endAt) {
        LocalDateTime now = LocalDateTime.now();

        this.member = member;
        this.seat = seat;
        this.reservationDate = reservationDate;
        this.startAt = startAt;
        this.endAt = endAt;
        this.registeredAt = now;

        validateDateAndTime(now);
    }

    private void validateDateAndTime(LocalDateTime now) {
        if (reservationDate.isBefore(now.toLocalDate())) {
            throw new IllegalArgumentException("예약 날짜는 오늘보다 이전일 수 없습니다.");
        }

        if (endAt.isBefore(startAt)) {
            throw new IllegalArgumentException("예약 시작 시각은 예약 종료 시각보다 이전이어야 합니다.");
        }

        if (reservationDate.isEqual(now.toLocalDate()) && startAt.isBefore(now.toLocalTime())) {
            throw new IllegalArgumentException("예약 시작 시각은 현재보다 이후여야 합니다.");
        }

    }
}
