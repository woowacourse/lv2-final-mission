package finalmission.infrastructure;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class RandomNameClient {

    private static final String BASE_URL = "https://randommer.io/api";
    private static final String X_API_KEY = "f813e67efb804f54af7f2211107fe297";

    private final RestClient restClient;

    public RandomNameClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(BASE_URL).build();
    }

    public String getFirstName() {
        var uri = "/Name?nameType=firstname&quantity=1";

        List<String> randomName = restClient.get()
                .uri(uri)
                .header("X-Api-Key", X_API_KEY)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> response.bodyTo(List.class));

        return randomName.getFirst();
    }
}
