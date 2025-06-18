package finalmission.integration;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.vo.LolName;
import finalmission.service.RiotRestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RiotRestClientTest {

    @Autowired
    private RiotRestClient riotRestClient;

    @DisplayName("존재하는 롤 닉네임 및 태그라면 true를 반환한다.")
    @Test
    void existsLolName() {
        // given
        final LolName lolName = new LolName("누신누황", "nunu");

        // when
        final boolean actual = riotRestClient.existsLolName(lolName);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("존재하지않는 롤 닉네임 및 태그라면 false를 반환한다.")
    @Test
    void existsLolNameFalse() {
        // given
        final LolName lolName = new LolName("누신누황123213", "nunu");

        // when
        final boolean actual = riotRestClient.existsLolName(lolName);

        // then
        assertThat(actual).isFalse();
    }
}
