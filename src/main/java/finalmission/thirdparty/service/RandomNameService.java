package finalmission.thirdparty.service;

import finalmission.global.error.exception.BadRequestException;
import finalmission.global.error.exception.ServerException;
import finalmission.thirdparty.handler.ClientErrorHandler;
import finalmission.thirdparty.handler.ServerErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class RandomNameService {

    private final RestClient restClient;

    @Transactional
    public String getRandomName() {
        try {
            String[] body = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("nameType", "fullname")
                            .queryParam("quantity", "1")
                            .build())
                    .retrieve()
                    .onStatus(new ClientErrorHandler())
                    .onStatus(new ServerErrorHandler())
                    .body(String[].class);

            if (body == null || body.length == 0) {
                throw new ServerException("응답이 비어 있습니다.");
            }

            System.out.println(body[0]);
            return body[0];
        } catch (BadRequestException | ServerException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException("랜덤 이름 생성 중 문제가 발생했습니다.");
        }
    }
}
