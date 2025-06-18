package finalmission.seat.domain;

import finalmission.common.exception.InvalidInputException;
import finalmission.venue.domain.Venue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer seatNumber;

    @ManyToOne
    private Venue venue;

    public Seat(final Integer seatNumber, final Venue venue) {
        validate(seatNumber, venue);
        this.seatNumber = seatNumber;
        this.venue = venue;
    }

    private void validate(final Integer seatNumber, final Venue venue) {
        if (seatNumber == null || seatNumber <= 0) {
            throw new InvalidInputException("좌석 번호는 null이거나 0 이하일 수 없습니다.");
        }
        if (venue == null) {
            throw new InvalidInputException("공연장은 null일 수 없습니다.");
        }
    }
}
