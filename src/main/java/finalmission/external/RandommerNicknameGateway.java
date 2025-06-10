package finalmission.external;

import finalmission.common.config.RandomNicknameProperties;
import finalmission.external.dto.RandomNameResponse;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@EnableConfigurationProperties(RandomNicknameProperties.class)
public class RandommerNicknameGateway implements RandomNicknameGateway {

    private final RestClient restClient;
    private final RandomNicknameProperties randomNicknameProperties;
    private final NicknameExceptionHandler nicknameExceptionHandler;

    public RandommerNicknameGateway(final RestClient restClient,
                                    final RandomNicknameProperties randomNicknameProperties,
                                    final NicknameExceptionHandler nicknameExceptionHandler) {
        this.restClient = restClient;
        this.randomNicknameProperties = randomNicknameProperties;
        this.nicknameExceptionHandler = nicknameExceptionHandler;
    }

    public RandomNameResponse findRandomNickname(NameType nameType) {
        List<String> body = restClient.get()
                .uri(randomNicknameProperties.getRandomUrl(), nameType.toLowerCase(), 1)
                .header("X-Api-Key", randomNicknameProperties.getApiKey())
                .retrieve()
                .onStatus(nicknameExceptionHandler)
                .body(List.class);
        return new RandomNameResponse(body);
    }
}
