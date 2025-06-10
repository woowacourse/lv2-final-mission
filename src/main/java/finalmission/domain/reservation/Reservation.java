package finalmission.domain.reservation;

import finalmission.domain.reservation.detail.Message;
import finalmission.domain.reservation.detail.NumberOfGuest;
import finalmission.domain.reservation.detail.ReservationDetail;
import finalmission.domain.reservation.owner.Owner;
import finalmission.domain.schedule.Schedule;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Owner owner;

    @Embedded
    private ReservationDetail detail;

    @ManyToOne
    private Schedule schedule;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public NumberOfGuest getNumberOfGuest() {
        return detail.getNumberOfGuest();
    }

    public Message getReservatonMessage() {
        return detail.getReservatonMessage();
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Reservation that)) {
            return false;
        }

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
