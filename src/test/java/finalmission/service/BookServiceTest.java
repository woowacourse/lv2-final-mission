package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(allBooks).hasSize(1);
    }


    @Test
    void 새_책_추가() {
        // given
        String name = "name2";
        String author = "author2";
        Long categoryId = 1L;
        int stock = 1;
        BookRequest bookRequest = new BookRequest(name, author, categoryId, stock);

        // when
        BookResponse bookResponse = bookService.addBook(bookRequest);

        // then
        assertThat(bookResponse.name()).isEqualTo(name);
        assertThat(bookResponse.author()).isEqualTo(author);
        assertThat(bookResponse.stock()).isEqualTo(stock);
    }

    @Test
    void 존재하는_책_추가() {
        // given
        String name = "name1";
        String author = "author1";
        Long categoryId = 1L;
        int stock = 3;
        BookRequest bookRequest = new BookRequest(name, author, categoryId, stock);

        // when
        BookResponse bookResponse = bookService.addBook(bookRequest);

        // then
        assertThat(bookResponse.name()).isEqualTo(name);
        assertThat(bookResponse.author()).isEqualTo(author);
        assertThat(bookResponse.category()).isEqualTo(categoryId);
        assertThat(bookResponse.stock()).isEqualTo(4);
    }
}
