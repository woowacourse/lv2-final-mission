package finalmission.repository;

import finalmission.domain.Store;
import finalmission.domain.StoreStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByStoreStatus(StoreStatus storeStatus);
}
