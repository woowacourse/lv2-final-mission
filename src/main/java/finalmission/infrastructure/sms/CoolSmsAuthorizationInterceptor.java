package finalmission.infrastructure.sms;

import java.time.Instant;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class CoolSmsAuthorizationInterceptor implements ClientHttpRequestInterceptor {

    @Value("${sms.coolSms.api-key}")
    private String apiKey;

    @Value("${sms.coolSms.secret-key}")
    private String secretKey;

    @Override
    @SneakyThrows
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {

        String date = Instant.now().toString();
        String salt = UUID.randomUUID().toString().replace("-", "");

        String message = date + salt;

        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"));
        String signature = bytesToHex(hmac.doFinal(message.getBytes()));

        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization",
                String.format("HMAC-SHA256 apiKey=%s, date=%s, salt=%s, signature=%s",
                        apiKey, date, salt, signature));

        return execution.execute(request, body);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
