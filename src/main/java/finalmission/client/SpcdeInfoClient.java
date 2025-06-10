package finalmission.client;

import finalmission.client.dto.SpcdeInfoConfirmRequest;
import finalmission.client.dto.SpcdeInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

@Component
public class SpcdeInfoClient {

    private final RestClient spcdeInfoClient;

    public SpcdeInfoClient(RestClient spcdeInfoClient) {
        this.spcdeInfoClient = spcdeInfoClient;
    }

    public ResponseEntity<SpcdeInfoResponse> confirmRestInfo(@RequestParam SpcdeInfoConfirmRequest request) {
        try {
            return spcdeInfoClient.post()
                    .uri("/SpcdeInfoService/getRestDeInfo")
                    .body(request)
                    .retrieve()
                    .toEntity(SpcdeInfoResponse.class);
        } catch (HttpStatusCodeException e) {
            throw new IllegalArgumentException("공휴일 조회 api 응답에 실패하였습니다.");
        }
    }
}
