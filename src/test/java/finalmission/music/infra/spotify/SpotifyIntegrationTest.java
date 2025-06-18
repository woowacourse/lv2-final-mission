package finalmission.music.infra.spotify;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.music.auth.controller.dto.LoginRequest;
import finalmission.music.infra.spotify.controller.dto.SpotifyAlbumSearchResponse;
import finalmission.music.infra.spotify.controller.dto.SpotifySearchRequest;
import finalmission.music.infra.spotify.service.dto.SimplifiedAlbumObject;
import finalmission.music.infra.spotify.service.dto.SimplifiedArtistObject;
import finalmission.music.infra.spotify.service.dto.SpotifyAlbums;
import finalmission.music.infra.spotify.service.dto.SpotifyToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(SpotifyClientTestConfig.class)
@AutoConfigureWireMock(port = 0)
public class SpotifyIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("스포티파이 API로 앨범을 검색할 수 있다.")
    void can_search_albums_with_spotify_api() throws JsonProcessingException {
        // given
        LoginRequest loginRequest = new LoginRequest("admin");
        String sessionId = RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(loginRequest)
            .when().post("/login")
            .then().log().all()
            .statusCode(200).extract().cookie("JSESSIONID");

        SpotifyToken spotifyToken = new SpotifyToken("test-access-token", "test-token", 1);
        SpotifyAlbumSearchResponse response = new SpotifyAlbumSearchResponse(
            new SpotifyAlbums(new SimplifiedAlbumObject[]{
                new SimplifiedAlbumObject("test-type", 0, "test-id", "test-name", "2025-05-24",
                    new SimplifiedArtistObject[]{
                        new SimplifiedArtistObject("test-artist")
                    })
            }));

        stubFor(post(urlEqualTo("/api/token"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(spotifyToken))));
        stubFor(get(urlPathEqualTo("/v1/search"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(response))));

        SpotifySearchRequest request = new SpotifySearchRequest("DONDA");

        // when-then
        SpotifyAlbumSearchResponse searchResponse = RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .header("Cookie", "JSESSIONID=" + sessionId)
            .body(request)
            .when().post("/spotify/search")
            .then().log().all()
            .statusCode(200).extract().body()
            .as(SpotifyAlbumSearchResponse.class);

        SimplifiedAlbumObject[] items = searchResponse.albums().items();

        Assertions.assertThat(items[0].name()).isEqualTo(response.albums().items()[0].name());
        Assertions.assertThat(items[0].album_type()).isEqualTo(response.albums().items()[0].album_type());
    }
}
