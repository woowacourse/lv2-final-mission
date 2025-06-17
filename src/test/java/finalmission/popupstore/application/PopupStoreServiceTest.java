package finalmission.popupstore.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import finalmission.popupstore.application.in.dto.OpenPopUpStore;
import finalmission.popupstore.application.out.PopupStoreRepository;
import finalmission.shopkeeper.application.out.ShopkeeperRepository;
import finalmission.shopkeeper.domain.Shopkeeper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class PopupStoreServiceTest {

    @Autowired
    private PopupStoreService popupStoreService;
    @Autowired
    private ShopkeeperRepository shopkeeperRepository;
    @Autowired
    private PopupStoreRepository popupStoreRepository;

    private static Shopkeeper createShopkeeper() {
        return Shopkeeper.create("상점주 이름");
    }

    @DisplayName("팝업스토어를 연다")
    @Test
    void openPopUpStore() {
        Shopkeeper shopkeeper = shopkeeperRepository.save(createShopkeeper());

        // given
        final OpenPopUpStore command = new OpenPopUpStore(
                "팝업스토어 제목",
                "팝업스토어 내용",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                100,
                shopkeeper.getId()
        );

        // when & then
        assertDoesNotThrow(() -> popupStoreService.open(command));
    }
}
