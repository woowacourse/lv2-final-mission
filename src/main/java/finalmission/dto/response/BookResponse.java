package finalmission.dto.response;

import finalmission.entity.Book;
import finalmission.entity.Category;

public record BookResponse(
        Long id,
        String name,
        String author,
        Category category,
        int inventory
) {
    public static BookResponse from(Book book) {
        return new BookResponse(book.getId(), book.getName(), book.getAuthor(), book.getCategory(), book.getInventory());
    }
}
