package finalmission.infrastructure;

import finalmission.domain.service.TimeInjection;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class LocalTimeInjection implements TimeInjection {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
