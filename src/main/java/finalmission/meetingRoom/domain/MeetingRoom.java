package finalmission.meetingRoom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int maximumTime;

    public void checkAvailableTime(LocalTime startAt, LocalTime endAt) {
        int endAtHour = endAt.getHour();
        int endAtMinute = endAt.getMinute();
        int startAtHour = startAt.getHour();
        int startAtMinute = startAt.getMinute();

        if (endAtHour - startAtHour > maximumTime || ((endAtHour - startAtHour == maximumTime)
                && endAtMinute > startAtMinute)) {
            throw new IllegalArgumentException("회의실 최대 예약 시간을 초과하였습니다.");
        }
    }
}
