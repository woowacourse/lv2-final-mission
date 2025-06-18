package finalmission.application;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestClockConfig {

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.of("Asia/Seoul"));
    }
}
