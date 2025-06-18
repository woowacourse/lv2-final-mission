package finalmission;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@SpringBootTest
class FinalMissionApplicationTests {

  @Autowired
  private RestClient restClient;

  @Test
  void contextLoads() {
    final String secretKey = "a5590a941dc94d8eb7ccb92065403da7";
    final String url = "https://randommer.io/api/Name?nameType=firstname&quantity=6";

    final String body = restClient.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .header("X-Api-Key", secretKey)
            .retrieve()
            .body(String.class);
    System.out.println(body);
  }
}
