package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        unique = true
    )
    private Ticket ticket;  // OneToOne 관계

    @Embedded
    private ReservationDetails details;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    private Reservation(
        final Long id,
        final Ticket ticket,
        final ReservationDetails details,
        final ReservationStatus status
    ) {
        validateNonNull(ticket, details, status);
        this.id = id;
        this.ticket = ticket;
        this.details = details;
        this.status = status;
    }

    private void validateNonNull(
        final Ticket ticket,
        final ReservationDetails details,
        final ReservationStatus status
    ) {
        if (ticket == null) {
            throw new IllegalArgumentException("티켓은 필수입니다.");
        }
        if (details == null) {
            throw new IllegalArgumentException("예약 정보는 필수입니다.");
        }
        if (status == null) {
            throw new IllegalArgumentException("예약 상태는 필수입니다.");
        }
    }

    public Reservation(
        final Ticket ticket,
        final ReservationDetails details
    ) {
        this(null, ticket, details, ReservationStatus.CONFIRMED);
    }

    public void cancel() {
        status = ReservationStatus.CANCELED;
    }
}
