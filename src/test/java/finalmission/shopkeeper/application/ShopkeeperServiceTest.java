package finalmission.shopkeeper.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import finalmission.shopkeeper.application.in.dto.SignupShopkeeper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ShopkeeperServiceTest {

    @Autowired
    private ShopkeeperService shopkeeperService;

    @DisplayName("팝업 스토어 주인을 등록한다")
    @Test
    void registerShopkeeper() {
        // given
        final String name = "주인장 이름";
        final SignupShopkeeper signupShopkeeper = new SignupShopkeeper(name);

        // when & then
        assertDoesNotThrow(() -> shopkeeperService.signUp(signupShopkeeper));
    }
}
