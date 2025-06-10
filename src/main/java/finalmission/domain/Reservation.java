package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Room room;
    @ManyToOne
    private ReservationTime reservationTime;

    public Reservation() {
    }

    public Reservation(Long id, LocalDate date, Member member, Room room, ReservationTime reservationTime) {
        this.id = id;
        this.date = date;
        this.member = member;
        this.room = room;
        this.reservationTime = reservationTime;
    }
}
