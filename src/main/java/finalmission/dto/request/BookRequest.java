package finalmission.dto.request;

import finalmission.entity.Book;
import finalmission.entity.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record BookRequest(
        String name,
        String author,
        Long category_id,
        int stock,
        int period
) {
}
