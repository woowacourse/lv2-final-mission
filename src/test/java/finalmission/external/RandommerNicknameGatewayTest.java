package finalmission.external;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import finalmission.common.config.RandomNicknameProperties;
import finalmission.common.config.RestClientConfig;
import finalmission.external.dto.RandomNameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@Import({RestClientConfig.class})
@RestClientTest(value = RandommerNicknameGateway.class)
class RandommerNicknameGatewayTest {

    @Autowired
    private RandomNicknameGateway randomNicknameGateway;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private RandomNicknameProperties randomNicknameProperties;

    private String url;

    @BeforeEach
    void setUp() {
        url = randomNicknameProperties.getBaseUrl();
    }

    @Test
    @DisplayName("test")
    void test() {
        // Given
        String result = """
                [
                "Mint"
                ]
                """;
        mockRestServiceServer.expect(requestTo(url + "/Name?nameType=firstname&quantity=1"))
                .andRespond(withSuccess(result, MediaType.APPLICATION_JSON));

        // When
        RandomNameResponse randomNickname = randomNicknameGateway.findRandomNickname(NameType.FIRSTNAME);

        // Then
//        assertThat(randomNickname.names().getFirst()).isEqualTo("Mint");
    }
}
