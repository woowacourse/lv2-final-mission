package finalmission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import finalmission.util.client.NameGeneratorClient;

class NameGeneratorClientTest {

    private NameGeneratorClient client;
    private MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
        customizer.customize(builder);
        RestClient restClient = builder.build();

        this.client = new NameGeneratorClient(
                "apikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikeyapikey",
                restClient
        );
        server = customizer.getServer();
    }

    @Test
    void getNames() throws JsonProcessingException {
        // given
        int amount = 10;
        server.expect(requestTo("https://randommer.io/api/Name?nameType=fullname&quantity=" + amount))
                .andRespond(withSuccess(new ObjectMapper().writeValueAsString(List.of(
                        "NAME1", "NAME2", "NAME3", "NAME4", "NAME5", "NAME6", "NAME7", "NAME8", "NAME9", "NAME10"
                )), MediaType.APPLICATION_JSON));

        // when
        List<String> names = client.getNames(amount);

        // then
        server.verify();
        assertThat(names).hasSize(amount);
        assertThat(names.getFirst()).isEqualTo("NAME1");
        assertThat(names.getLast()).isEqualTo("NAME10");
    }
}
