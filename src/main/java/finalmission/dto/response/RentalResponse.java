package finalmission.dto.response;

import finalmission.entity.Book;
import finalmission.entity.Member;
import finalmission.entity.Rental;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

public record RentalResponse(
        Long id,
        Member member,
        Book book,
        LocalDate rentalDate,
        LocalDate returnDate
) {
    public static RentalResponse from(Rental rental) {
        return new RentalResponse(rental.getId(), rental.getMember(), rental.getBook(), rental.getRentalDate(), rental.getReturnDate());
    }
}
