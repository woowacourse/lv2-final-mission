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
            bookRepository.addInventoryById(bookId, bookRequest.inventory());
            return BookResponse.from(bookRepository.findById(bookId).get());
        }
        Category category = categoryRepository.findById(bookRequest.category_id()).get();
        Book book = new Book(bookRequest.name(), bookRequest.author(), category, bookRequest.inventory(), bookRequest.period());
        return BookResponse.from(bookRepository.save(book));
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 책이 없습니다."));
    }

    public int findPeriodById(Long id) {
        return bookRepository.findPeriodById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 책이 없습니다."));
    }

    public int getAvailableStock(Long bookId) {
        return bookRepository.getAvailableStock(bookId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 책이 없습니다."));
    }

    public void validateAvailableStock(Long bookId) {
        int availableStock = getAvailableStock(bookId);
        if (availableStock <= 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
    }
}
