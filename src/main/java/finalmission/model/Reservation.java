package finalmission.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private Seat seat;

    private LocalDateTime registeredAt;

    @Embedded
    private ReservationSchedule reservationSchedule;

    public boolean isOwnedBy(Member member) {
        return member.getId().equals(this.member.getId());
    }

    public void update(Reservation updatedReservation) {
        this.registeredAt = updatedReservation.getRegisteredAt();
        this.reservationSchedule = updatedReservation.getReservationSchedule();
    }

    public Reservation(Member member, Seat seat, ReservationSchedule reservationSchedule) {
        LocalDateTime now = LocalDateTime.now();

        this.member = member;
        this.seat = seat;
        this.reservationSchedule = reservationSchedule;
        this.registeredAt = now;
    }

    public LocalDate getReservationDate() {
        return reservationSchedule.getReservationDate();
    }

    public LocalTime getStartAt() {
        return reservationSchedule.getStartAt();
    }

    public LocalTime getEndAt() {
        return reservationSchedule.getEndAt();
    }
}
