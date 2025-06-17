package finalmission.fixture;

import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

public final class MailClientFixture {

    private MailClientFixture() {
    }

    public static final String BASE_URL = "https://api.mailgun.net/v3/sandbox493a7c6d7f3b418ba011700537536d77.mailgun.org/messages";

    public static RestClient.Builder TEST_CLIENT = RestClient.builder()
            .baseUrl(BASE_URL);

    public static MockRestServiceServer MOCK_SERVER = MockRestServiceServer
            .bindTo(TEST_CLIENT)
            .build();
}
