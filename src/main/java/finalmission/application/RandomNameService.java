package finalmission.application;

import finalmission.infrastructure.RandomNameClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RandomNameService {

    private final RandomNameClient randomNameClient;

    public String getFirstName() {
        return randomNameClient.getFirstName();
    }
}
