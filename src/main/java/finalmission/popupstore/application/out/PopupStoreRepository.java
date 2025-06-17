package finalmission.popupstore.application.out;

import finalmission.popupstore.domain.PopupStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopupStoreRepository extends JpaRepository<PopupStore, Long> {
}
