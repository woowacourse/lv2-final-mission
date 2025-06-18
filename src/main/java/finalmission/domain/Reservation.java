package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public Reservation(Member member, Room room, LocalDate date, LocalTime startTime, LocalTime endTime) {
        validateTime(startTime, endTime);
        this.member = member;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isDuplicateTime(LocalTime startTime, LocalTime endTime) {
        return !(!endTime.isAfter(this.startTime) || !startTime.isBefore(this.endTime));
    }

    public boolean matchDate(LocalDate date) {
        return this.date.equals(date);
    }

    public void update(Room room, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateTime(LocalTime startTime, LocalTime endTime) {
        validateTimeUnit(startTime, endTime);
        validateTimeRange(startTime, endTime);
        validateSameAndOverTime(startTime, endTime);
    }

    private void validateTimeUnit(LocalTime startTime, LocalTime endTime) {
        if (startTime.getMinute() % 10 != 0 || endTime.getMinute() % 10 != 0) {
            throw new IllegalArgumentException("예약 시간은 10분 단위만 가능합니다.");
        }
    }

    private void validateTimeRange(LocalTime startTime, LocalTime endTime) {
        Duration between = Duration.between(startTime, endTime);
        if (between.toMinutes() > 60) {
            throw new IllegalArgumentException("예약 시간은 1시간을 초과할 수 없습니다.");
        }
    }

    private void validateSameAndOverTime(LocalTime startTime, LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("예약 시작 시간은 종료 시간과 같거나 이후일 수 없습니다.");
        }
    }

    public String getStartTime(DateTimeFormatter formatter) {
        return startTime.format(formatter);
    }

    public String getEndTime(DateTimeFormatter formatter) {
        return endTime.format(formatter);
    }
}
