package finalmission.client;

import finalmission.domain.reservation.owner.Nickname;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandommerNicknameClient implements RandomNicknameGenerator {

    private final RestClient restClient;

    public RandommerNicknameClient(@Value("${randommer.secret-key}") final String secretKey) {
        this.restClient = RestClient.builder()
                .baseUrl("https://randommer.io/api/Name?nameType=firstname&quantity=1")
                .defaultHeader("X-Api-Key", secretKey)
                .build();
    }

    @Override
    public Nickname generate() {
        final String[] nicknames = restClient.get()
                .retrieve()
                .body(String[].class);
        return new Nickname(nicknames[0]);
    }
}

