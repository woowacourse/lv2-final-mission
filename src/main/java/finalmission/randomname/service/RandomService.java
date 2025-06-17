package finalmission.randomname.service;

import org.springframework.stereotype.Service;

@Service
public class RandomService {

    private final RandomNameRestClient restClient;

    public RandomService(RandomNameRestClient restClient) {
        this.restClient = restClient;
    }

    public String createRandomName() {
        return restClient.makeRandomName();
    }
}
