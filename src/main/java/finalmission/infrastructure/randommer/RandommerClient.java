package finalmission.infrastructure.randommer;

import finalmission.dto.request.RandommerRequest;
import java.util.List;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@DependsOn("randommerRestClient")
public class RandommerClient {

    private final RestClient restClient;

    public RandommerClient(final RestClient restClient) {
        this.restClient = restClient;
    }

    public List getRandomName(RandommerRequest randommerRequest) {
        return restClient.get()
                .uri("/Name?nameType={type}&quantity={quantity}",
                        randommerRequest.nameType(),
                        randommerRequest.quantity()
                )
                .retrieve()
                .body(List.class);
    }
}
