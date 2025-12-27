package library.borrow.dto;

import java.time.LocalDate;
import library.borrow.domain.Borrow;

public record BorrowResponse(
    Long id,
    Long collectionId,
    String memberEmail,
    LocalDate dueDate
) {
    public static BorrowResponse from(Borrow borrow) {
        return new BorrowResponse(
            borrow.getId(),
            borrow.getCollection().getId(),
            borrow.getMember().getEmail(),
            borrow.getDueDate()
        );
    }
} 