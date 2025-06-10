package finalmission.reservation.domain;

import finalmission.meetingRoom.domain.MeetingRoom;
import finalmission.member.domain.Member;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private MeetingRoom meetingRoom;

    @Embedded
    private ReservationDate date;

    @Embedded
    private StartAt startAt;

    @Embedded
    private EndAt endAt;

    public Reservation(Member member, MeetingRoom meetingRoom, ReservationDate date, StartAt startAt, EndAt endAt) {
        this.member = member;
        this.meetingRoom = meetingRoom;
        this.date = date;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public LocalDate getDate() {
        return date.getValue();
    }

    public LocalTime getStartAt() {
        return startAt.getValue();
    }

    public LocalTime getEndAt() {
        return endAt.getValue();
    }

    public void updateDate(ReservationDate date) {
        this.date = date;
    }

    public void updateStartAt(StartAt startAt) {
        this.startAt = startAt;
    }

    public void updateEndAt(EndAt endAt) {
        this.endAt = endAt;
    }
}
