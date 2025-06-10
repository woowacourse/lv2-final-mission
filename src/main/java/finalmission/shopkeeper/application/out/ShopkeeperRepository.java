package finalmission.shopkeeper.application.out;

import finalmission.shopkeeper.domain.Shopkeeper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopkeeperRepository extends JpaRepository<Shopkeeper, Long> {
}
