package finalmission.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class ReservationSchedule {

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private LocalTime startAt;

    @Column(nullable = false)
    private LocalTime endAt;

    public ReservationSchedule(LocalDate reservationDate, LocalTime startAt, LocalTime endAt) {
        this.reservationDate = reservationDate;
        this.startAt = startAt;
        this.endAt = endAt;

        validateDateAndTime(LocalDateTime.now());
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
