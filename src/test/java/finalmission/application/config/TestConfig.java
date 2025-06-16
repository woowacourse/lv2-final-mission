package finalmission.application.config;

import finalmission.application.stunt.StubAnniversaryRepository;
import finalmission.domain.AnniversaryRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public AnniversaryRepository anniversaryRepository() {
        return new StubAnniversaryRepository(List.of(LocalDate.of(2025, 5, 5)));
    }
}
