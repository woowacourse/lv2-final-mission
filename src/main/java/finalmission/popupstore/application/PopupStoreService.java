package finalmission.popupstore.application;

import finalmission.popupstore.domain.PopupStore;
import finalmission.popupstore.dto.CreatePopUpStoreIn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PopupStoreService {

    public final PopupStoreRepository popupStoreRepository;

    @Transactional
    public void open(final CreatePopUpStoreIn command) {
        PopupStore opened = PopupStore.open(
                command.title(),
                command.content(),
                command.startAt(),
                command.endAt(),
                command.maxEnteredMemberCount()
        );

        popupStoreRepository.save(opened);
    }
}
