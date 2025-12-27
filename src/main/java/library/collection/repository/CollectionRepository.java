package library.collection.repository;

import java.util.List;
import java.util.Optional;
import library.collection.domain.Collection;

public interface CollectionRepository {

    List<Collection> findByBookId(Long bookId);

    Optional<Collection> findById(Long id);
    
    Collection save(Collection collection);

}
