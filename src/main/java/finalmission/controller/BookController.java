package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.common.Role;
import finalmission.dto.request.BookRequest;
import finalmission.dto.response.BookResponse;
import finalmission.service.BookService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @CheckRole(Role.ADMIN)
    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        BookResponse bookResponse = bookService.addBook(bookRequest);
        return ResponseEntity.created(URI.create("/books/" + bookResponse.id())).build();
    }
}
