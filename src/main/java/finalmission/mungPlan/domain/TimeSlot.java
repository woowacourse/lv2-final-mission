package finalmission.mungPlan.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeSlot {
    private LocalTime startAt;
    private LocalTime endAt;

    //TODO: startAt, endAt 검증 - 끝나는시간이 시작시간보다 늦는지
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSlot timeSlot)) {
            return false;
        }
        return Objects.equals(startAt, timeSlot.startAt) && Objects.equals(endAt, timeSlot.endAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAt, endAt);
    }
}
