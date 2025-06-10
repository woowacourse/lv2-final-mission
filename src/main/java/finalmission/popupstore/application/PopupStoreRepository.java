package finalmission.popupstore.application;

import finalmission.popupstore.domain.PopupStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopupStoreRepository extends JpaRepository<PopupStore, Long> {
}
