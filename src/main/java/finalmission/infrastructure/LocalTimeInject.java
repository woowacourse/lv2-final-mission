package finalmission.infrastructure;

import finalmission.domain.service.TimeInject;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class LocalTimeInject implements TimeInject {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
