package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.dto.request.BookRequest;
import finalmission.dto.response.BookResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void 책_목록_조회() {

        // given & when
        List<BookResponse> allBooks = bookService.getAllBooks();
        assertThat(allBooks).hasSize(2);
    }


    @Test
    void 새_책_추가() {
        // given
        String name = "name2";
        String author = "author2";
        Long categoryId = 1L;
        int inventory = 1;
        int period = 14;
        BookRequest bookRequest = new BookRequest(name, author, categoryId, inventory, period);

        // when
        BookResponse bookResponse = bookService.addBook(bookRequest);

        // then
        assertThat(bookResponse.name()).isEqualTo(name);
        assertThat(bookResponse.author()).isEqualTo(author);
        assertThat(bookResponse.inventory()).isEqualTo(inventory);
    }

    @Test
    void 존재하는_책_추가() {
        // given
        String name = "name1";
        String author = "author1";
        Long categoryId = 1L;
        int inventory = 3;
        int period = 14;
        BookRequest bookRequest = new BookRequest(name, author, categoryId, inventory,  period);

        // when
        bookService.addBook(bookRequest);
        BookResponse bookResponse = bookService.addBook(bookRequest);

        // then
        assertThat(bookResponse.name()).isEqualTo(name);
        assertThat(bookResponse.author()).isEqualTo(author);
        assertThat(bookResponse.inventory()).isEqualTo(6);
    }
}
