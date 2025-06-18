package finalmission.infra.thirdparty;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SendGridRestClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String sendgridKey;

    public SendGridRestClient(
            @Value("${sendgrid.base-url}") String baseUrl,
            @Value("${sendgrid.api-key}") String sendgridKey,
            @Qualifier("SendGridRestTemplate") RestTemplate restTemplate,
            SendGridErrorResponseFilter errorHandler
    ) {
        this.baseUrl = baseUrl;
        this.sendgridKey = sendgridKey;
        this.restTemplate = restTemplate;
        this.restTemplate.setErrorHandler(errorHandler);
    }

    public ResponseEntity<String> getEmailResponse(SendEmailRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", makeAuthHeader());

        HttpEntity<SendEmailRequest> entity = new HttpEntity<>(request, headers);
        return restTemplate.postForEntity(baseUrl + "/v3/mail/send", entity, String.class);
    }

    private String makeAuthHeader() {
        return "Bearer " + sendgridKey;
    }
}
