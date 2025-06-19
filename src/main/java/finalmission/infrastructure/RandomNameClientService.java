package finalmission.infrastructure;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.service.annotation.GetExchange;

@Component
public interface RandomNameClientService {

    @GetExchange("/Name?nameType=surname&quantity=10")
    List<String> generate();
}
