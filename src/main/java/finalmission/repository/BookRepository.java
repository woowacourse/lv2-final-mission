package finalmission.repository;

import finalmission.entity.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByName(String name);

    Optional<Book> findByName(String name);

    @Modifying
    @Transactional
    @Query("""
            UPDATE Book b
               SET b.inventory = b.inventory + :inventory
               WHERE b.id = :id
            """)
    int addInventoryById(@Param("id") Long id, @Param("inventory") int inventory);

    @Query("SELECT b.period FROM Book b WHERE b.id = :id")
    Optional<Integer> findPeriodById(@Param("id") Long id);

    @Query("""
            SELECT b.inventory - COUNT(*)
            FROM Book b
            LEFT JOIN Rental r ON b.id = r.book.id
            WHERE b.id = :bookId
            GROUP BY b.id
            """)
    Optional<Integer> getAvailableStock(@Param("bookId") Long bookId);
}
