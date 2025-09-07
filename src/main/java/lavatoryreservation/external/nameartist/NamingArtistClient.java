package lavatoryreservation.external.nameartist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NamingArtistClient {

    private final String newNameRequestUri;
    private final RestClient restClient;
    private final String nameType;
    private final int quantity;

    public NamingArtistClient(@Value("${namer.api.key}") String apiKey,
                              @Value("${namer.api.baseUrl}") String baseUrl,
                              @Value("${namer.api.newname.uri}") String newNameRequestUri,
                              @Value("${namer.api.nametype}") String nameType,
                              @Value("${namer.api.quantity}") int quantity,
                              @Value("${namer.api.key.header}") String apiKeyHeader) {

        this.newNameRequestUri = newNameRequestUri;
        this.nameType = nameType;
        this.quantity = quantity;

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(apiKeyHeader, apiKey)
                .build();
    }

    public String getNewName() {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(newNameRequestUri)
                        .queryParam("nameType", nameType)
                        .queryParam("quantity", quantity)
                        .build())
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }
}
