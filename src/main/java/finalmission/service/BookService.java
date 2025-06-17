package finalmission.service;

import finalmission.dto.request.BookRequest;
import finalmission.dto.response.BookResponse;
import finalmission.entity.Book;
import finalmission.entity.Category;
import finalmission.repository.BookRepository;
import finalmission.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::from)
                .toList();
    }

    public BookResponse addBook(BookRequest bookRequest) {
        if (bookRepository.existsByName(bookRequest.name())) {
            Long bookId = bookRepository.findByName(bookRequest.name()).get().getId();
            bookRepository.addStockById(bookId, bookRequest.stock());
            return BookResponse.from(bookRepository.findById(bookId).get());
        }
        Category category = categoryRepository.findById(bookRequest.category_id()).get();
        Book book = new Book(bookRequest.name(), bookRequest.author(),  category, bookRequest.stock(), bookRequest.period());
        return BookResponse.from(bookRepository.save(book));
    }
}
