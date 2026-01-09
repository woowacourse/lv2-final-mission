package finalmission.reservation;

import finalmission.meetingroom.MeetingRoom;
import finalmission.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private ReservationTime time;

    private LocalDate date;

    public Reservation() {
    }

    public Reservation(Long id, Member member, MeetingRoom meetingRoom, ReservationTime time,
        LocalDate date) {
        this.id = id;
        this.member = member;
        this.meetingRoom = meetingRoom;
        this.time = time;
        this.date = date;
    }

    public Reservation(Member member, MeetingRoom meetingRoom, ReservationTime time,
        LocalDate date) {
        this(null, member, meetingRoom, time, date);
    }

    public boolean isReservationTime(ReservationTime time) {
        return this.time == time;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public ReservationTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isBy(Member member) {
        return this.member.equals(member);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
