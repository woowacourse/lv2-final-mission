package finalmission.member.application.out.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandommerClient {

    @Value("${randommer.url}")
    private String randommerApiUrl;

    @Value("${randommer.api-key}")
    private String randommerApiKey;

    public String requestRandomNickname() {
        return RestClient.create()
                .get()
                .uri(randommerApiUrl + "/Name?nameType=fullname&quantity=1")
                .header("X-Api-Key", randommerApiKey)
                .retrieve()
                .body(List.class)
                .get(0).toString();
    }
}
