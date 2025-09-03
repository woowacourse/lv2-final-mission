package finalmission.service;

import finalmission.client.RandommerClient;
import org.springframework.stereotype.Service;

@Service
public class RandommerService {
    private final RandommerClient randommerClient;

    public RandommerService(final RandommerClient randommerClient) {
        this.randommerClient = randommerClient;
    }

    public String generateRandomName() {
        return randommerClient.getSingleRandomName();
    }
}
