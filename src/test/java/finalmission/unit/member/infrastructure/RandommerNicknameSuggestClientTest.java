package finalmission.unit.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import finalmission.member.infrastructure.RandommerNicknameSuggestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(value = {RandommerNicknameSuggestClient.class})
@TestPropertySource(properties = {
        "nickname-suggestion.randommer.secret-key=test-secret-key",
        "nickname-suggestion.randommer.base-url=https://randommer.io"
})
class RandommerNicknameSuggestClientTest {

    @Autowired
    private RandommerNicknameSuggestClient nicknameSuggestClient;

    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    void 닉네임_요청_결과가_성공이면_닉네임을_반환한다() {
        // given
        String expectedNickname = "John";
        mockServer.expect(requestTo("https://randommer.io/api/Name?nameType=firstname&quantity=1"))
                .andExpect(header("X-Api-Key", "test-secret-key"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(APPLICATION_JSON)
                        .body("[\"" + expectedNickname + "\"]"));
        // when
        String nickname = nicknameSuggestClient.getNickname();
        // then
        assertThat(nickname).isEqualTo(expectedNickname);
    }

    @Test
    void 닉네임_요청_결과가_에러면_예외가_발생한다() {
        // given
        mockServer.expect(requestTo("https://randommer.io/api/Name?nameType=firstname&quantity=1"))
                .andExpect(header("X-Api-Key", "test-secret-key"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));
        // when & then
        assertThatThrownBy(() -> nicknameSuggestClient.getNickname())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("외부 API 통신 에러가 발생했습니다.");
    }
}
