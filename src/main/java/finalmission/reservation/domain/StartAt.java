package finalmission.reservation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class StartAt {
    public static final LocalTime OPEN_TIME = LocalTime.of(10, 0);

    @Column(name = "start_at", nullable = false)
    private LocalTime value;

    public StartAt(LocalTime value) {
        validateStartAt(value);
        this.value = value;
    }

    private void validateStartAt(LocalTime startAt) {
        if (startAt.isBefore(OPEN_TIME)) {
            throw new IllegalArgumentException("예약 가능 시작 시간은 오전 10시입니다");
        }
    }
}
