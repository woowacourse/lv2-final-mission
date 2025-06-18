package finalmission.service;

import finalmission.domain.vo.LolName;
import finalmission.exception.NotFoundException;
import finalmission.service.dto.RiotPuuidResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;

@Component
public class RiotRestClient {

    private static final String RIOT_API_KEY = "RGAPI-3feb1d2c-b3d2-4dd4-88f9-b849d5830f04";

    private final RestClient restClient;

    public RiotRestClient(final Builder restClientBuilder) {
        restClient = restClientBuilder
                .defaultStatusHandler(new RiotRestClientErrorHandler())
                .defaultHeader("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36")
                .defaultHeader("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .defaultHeader("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                .defaultHeader("Origin", "https://developer.riotgames.com")
                .defaultHeader("X-Riot-Token", RIOT_API_KEY)
                .defaultHeader("Content-Type", "application/json")
                .baseUrl("https://asia.api.riotgames.com/riot")
                .build();
    }

    public boolean existsLolName(final LolName name) {
        try {
            getPuuid(name);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    private String getPuuid(final LolName lolName) {
        final RiotPuuidResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path(
                                "/account/v1/accounts/by-riot-id/{playerName}/{playerTag}")
                        .queryParam("api_key", RIOT_API_KEY)
                        .build(lolName.getPlayerName(), lolName.getPlayerTag()))
                .retrieve()
                .body(RiotPuuidResponse.class);

        return response.puuid();
    }
}
