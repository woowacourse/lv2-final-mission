package finalmission.popupstore.application;

import finalmission.popupstore.domain.PopupStore;
import finalmission.popupstore.dto.CreatePopUpStoreIn;
import finalmission.shopkeeper.application.ShopkeeperRepository;
import finalmission.shopkeeper.domain.Shopkeeper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PopupStoreService {

    public final PopupStoreRepository popupStoreRepository;
    private final ShopkeeperRepository shopkeeperRepository;

    @Transactional
    public void open(final CreatePopUpStoreIn command) {
        Shopkeeper shopkeeper = shopkeeperRepository.findById(command.shopkeeperId())
                .orElseThrow();

        PopupStore opened = PopupStore.open(
                command.title(),
                command.content(),
                command.startAt(),
                command.endAt(),
                command.maxEnteredMemberCount(),
                shopkeeper
        );

        popupStoreRepository.save(opened);
    }
}
