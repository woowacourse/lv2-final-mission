package finalmission.member.infrastructure;

import finalmission.member.application.RandomNameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalRandomNameGenerator implements RandomNameGenerator {
    private final RandomNameClient randomNameClient;

    @Override
    public String generateName() {
        return randomNameClient.getRandomName();
    }
}
