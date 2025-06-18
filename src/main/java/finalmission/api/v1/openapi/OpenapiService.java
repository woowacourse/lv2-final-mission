package finalmission.api.v1.openapi;

import finalmission.api.v1.openapi.infastructure.OpenApiRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenapiService {

    private final OpenApiRestClient openApiRestClient;

    public String getNickname() {
        return openApiRestClient.getName()
                .nickname();
    }
}
