package finalmission.dto.request;

import finalmission.entity.Book;
import finalmission.entity.Member;
import finalmission.entity.Rental;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

public record RentalRequest(
        Long memberId,
        Long bookId,
        LocalDate rentalDate,
        LocalDate returnDate
) {
}
