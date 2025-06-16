package finalmission.customer.service;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandomNameRestClient {

    public String getRandomName() {
        return Objects.requireNonNull(RestClient.create()
                        .get()
                        .uri("https://randommer.io/api/Name?nameType=fullname&quantity=1")
                        .header("X-API-KEY", "59b351eda5d24b6c84e80c9130833a20")
                        .retrieve()
                        .body(List.class))
                .getFirst().toString();
    }
}
