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
public class EndAt {
    public static final LocalTime CLOSE_TIME = LocalTime.of(22, 30);

    @Column(name = "end_at", nullable = false)
    private LocalTime value;

    public EndAt(LocalTime value) {
        validateEndAt(value);
        this.value = value;
    }

    private void validateEndAt(LocalTime startAt) {
        if (startAt.isAfter(CLOSE_TIME)) {
            throw new IllegalArgumentException("예약 가능 마감 시간은 오후 10시 30분입니다");
        }
    }
}
