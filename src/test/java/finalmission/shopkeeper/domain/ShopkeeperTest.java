package finalmission.shopkeeper.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ShopkeeperTest {

    @Test
    void 팝업_스토어_주인을_생성한다() {
        // given
        final String name = "주인장 이름";

        // when
        final Shopkeeper shopkeeper = Shopkeeper.create(name);

        // then
        assertThat(shopkeeper.getName()).isEqualTo(name);
    }
}
