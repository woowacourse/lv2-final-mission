package finalmission.client;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.reservation.owner.Nickname;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class RandommerNicknameClientTest {

    @Value("${randommer.secret-key}")
    private String secretKey;

    @DisplayName("외부 API를 이용하여 랜덤 닉네임을 생성할 수 있다.")
    @Test
    void testRandommerNicknameClient() {
        // given
        RandommerNicknameClient randommerNicknameClient = new RandommerNicknameClient(secretKey);
        // when
        Nickname actualResponse = randommerNicknameClient.generate();
        // then
        assertThat(actualResponse.getValue()).isNotNull();
    }
}
