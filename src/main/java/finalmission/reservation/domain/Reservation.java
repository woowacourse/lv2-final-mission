package finalmission.reservation.domain;

import finalmission.meetingroom.domain.MeetingRoom;
import finalmission.member.domian.Member;
import finalmission.reservationtime.domain.ReservationTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private MeetingRoom meetingRoom;


    protected Reservation() {

    }

    public Reservation(LocalDate date, ReservationTime time, Member member, MeetingRoom meetingRoom) {
        this.date = date;
        this.time = time;
        this.member = member;
        this.meetingRoom = meetingRoom;
    }
}
