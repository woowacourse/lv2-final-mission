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
               SET b.stock = b.stock + :stock
                  WHERE b.id = :id
            """)
    int addStockById(@Param("id") Long id, @Param("stock") int stock);
}
