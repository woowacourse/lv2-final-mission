package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import finalmission.room.domain.Room;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime time;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Member member;

    private Reservation() {
    }

    public Reservation(Long id, LocalDate date, LocalTime time, Room room, Member member) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.room = room;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Room getRoom() {
        return room;
    }

    public Member getMember() {
        return member;
    }
}
