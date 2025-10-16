package shh.alias.infrastructure;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import shh.alias.application.AliasClient;

@Component
@RequiredArgsConstructor
public class RandomAliasClient implements AliasClient {

    @Qualifier("randommerAliasRestClient")
    private final RestClient restClient;

    @Override
    public String requestAlias(final Integer quantity) {
        return Objects.requireNonNull(restClient.get()
                        .uri("Name?nameType=firstname&quantity=" + quantity.toString())
                        .retrieve()
                        .body(String.class))
                .replaceAll("^\\[\"|\"\\]$", "");
    }
}
