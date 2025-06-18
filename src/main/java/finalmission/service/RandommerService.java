package finalmission.service;

import finalmission.dto.request.RandommerRequest;
import finalmission.infrastructure.randommer.RandommerClient;
import org.springframework.stereotype.Service;

@Service
public class RandommerService {
    private final RandommerClient randommerClient;

    public RandommerService(final RandommerClient randommerClient) {
        this.randommerClient = randommerClient;
    }

    public String generateRandomFirstName() {
        RandommerRequest request = new RandommerRequest("firstName", 1);

        return (String) randommerClient.getRandomName(request).getFirst();
    }
}
