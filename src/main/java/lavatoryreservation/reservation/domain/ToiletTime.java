package lavatoryreservation.reservation.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lavatoryreservation.exception.ReservationException;

@Embeddable
public class ToiletTime {

    private static final LocalTime RESERVATION_START_TIME = LocalTime.of(6, 0);
    private static final LocalTime RESERVATION_OVER_TIME = LocalTime.of(23, 0);

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ToiletTime(LocalDateTime startTime, LocalDateTime endTime) {
        validateTime(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime.isBefore(startTime)) {
            throw new ReservationException("유효하지 않은 예약 시간입니다");
        }
        if (endTime.toLocalTime().isAfter(RESERVATION_OVER_TIME) ||
                startTime.toLocalTime().isBefore(RESERVATION_START_TIME)) {
            throw new ReservationException("유효하지 않은 예약 시간입니다");
        }
    }

    protected ToiletTime() {

    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
