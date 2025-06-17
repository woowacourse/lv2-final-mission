package finalmission.popupstore.domain;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.shopkeeper.domain.Shopkeeper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PopupStoreTest {

    public static Shopkeeper createShopkeeper() {
        return Shopkeeper.create("주인장 이름");
    }

    @Test
    void 팝업스토어를_생성한다() {
        // given
        final String title = "팝업스토어 타이틀";
        final String description = "팝업스토어 설명";
        final int maxReservations = 20;
        final LocalDateTime startAt = LocalDateTime.now();
        final LocalDateTime endAt = startAt.plusDays(1);
        Shopkeeper shopkeeper = createShopkeeper();

        // when
        PopupStore popupStore = PopupStore.open(
                title,
                description,
                startAt,
                endAt,
                maxReservations,
                shopkeeper
        );

        // then
        assertThat(popupStore.getTitle()).isEqualTo(title);
        assertThat(popupStore.getContent()).isEqualTo(description);
        assertThat(popupStore.getStartAt()).isEqualTo(startAt);
        assertThat(popupStore.getEndAt()).isEqualTo(endAt);
        assertThat(popupStore.getMaxEnteredMemberCount()).isEqualTo(maxReservations);
        assertThat(popupStore.getShopkeeper()).isEqualTo(shopkeeper);
    }
}
