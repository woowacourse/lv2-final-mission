package finalmission.client;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NameClientTest {

    @Autowired
    NameClient nameClient;

    @Test
    @DisplayName("api로 랜덤 이름을 가져온다")
    void getRandom() {
        String name = nameClient.getRandomName();
        Assertions.assertThat(name).hasSizeGreaterThan(0);
    }

}
