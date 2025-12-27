package library.collection.repository;

import library.collection.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaCollectionRepository extends CollectionRepository, JpaRepository<Collection, Long> {


}
