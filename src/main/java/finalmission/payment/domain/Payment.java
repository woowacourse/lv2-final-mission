package finalmission.payment.domain;

import finalmission.common.exception.InvalidInputException;
import finalmission.reservation.domain.Reservation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tid;

    @Column(nullable = false)
    private Long amount;

    @OneToOne
    @JoinColumn(nullable = false)
    private Reservation reservation;

    private String requestedAt;

    private String approvedAt;

    public Payment(final String tid, final Long amount, final Reservation reservation) {
        validate(tid, amount, reservation);
        this.tid = tid;
        this.amount = amount;
        this.reservation = reservation;
    }

    public Payment(final String tid, final Long amount, final Reservation reservation,
                   final String requestedAt,
                   final String approvedAt) {
        validate(tid, amount, reservation);
        this.tid = tid;
        this.amount = amount;
        this.reservation = reservation;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }

    public void validate(final String tid, final Long amount, final Reservation reservation) {
        if (tid == null || tid.isEmpty()) {
            throw new InvalidInputException("tid는 null이거나 빈 값일 수 없습니다.");
        }
        if (amount <= 0) {
            throw new InvalidInputException("amount는 0보다 커야 합니다.");
        }
        if (reservation == null) {
            throw new InvalidInputException("reservation은 null일 수 없습니다.");
        }
    }
}
