package finalmission.client;

import static finalmission.fixture.MailClientFixture.BASE_URL;
import static finalmission.fixture.MailClientFixture.MOCK_SERVER;
import static finalmission.fixture.MailClientFixture.TEST_CLIENT;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import finalmission.client.dto.SendEmailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class MailgunClientTest {

    private final MailgunClient mailgunClient = new MailgunClient(TEST_CLIENT.build());

    @BeforeEach
    void setUp() {
        MOCK_SERVER.reset();
    }

    @Test
    void 메일을_전송할_수_있다() {
        // given
        SendEmailRequest request = new SendEmailRequest("test@email.com", "title", "content");

        MOCK_SERVER.expect(requestTo(BASE_URL + appendEmailQueryParameters(request)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK));
        // when & then
        mailgunClient.sendEmail(request);
    }

    private String appendEmailQueryParameters(SendEmailRequest request) {
        String queryParameters = "?from=%s&to=%s&subject=%s&text=%s";
        return String.format(queryParameters,
                "postmaster@sandbox493a7c6d7f3b418ba011700537536d77.mailgun.org",
                request.to(),
                request.subject(),
                request.content());
    }
}
