package finalmission.room;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import finalmission.helper.TestHelper;
import finalmission.room.dto.request.RoomRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoomAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        TestHelper.signUpAsAdmin();
    }

    @Test
    @DisplayName("회의실을 생성한다. (어드민 권한)")
    void createRoom() {
        // given
        String token = TestHelper.loginAsAdmin();

        String name = "회의실";
        var request = new RoomRequest(name);

        // when & then
        TestHelper.postWithToken("/rooms", request, token)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", is(name));
    }

    @Test
    @DisplayName("모든 회의실을 조회한다.")
    void findAllRooms() {
        // given
        String token = TestHelper.loginAsAdmin();

        TestHelper.postWithToken("/rooms", new RoomRequest("회의실"), token);

        // when & then
        TestHelper.getWithToken("/rooms", token)
                .then()
                .body("$", hasSize(1));
    }

    @Test
    @DisplayName("특정 회의실을 조회한다.")
    void findRoomById() {
        // given
        String token = TestHelper.loginAsAdmin();

        String name = "회의실";
        var id = TestHelper.postWithToken("/rooms", new RoomRequest(name), token)
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("id");

        // when & then
        TestHelper.getWithToken("/rooms/" + id, token)
                .then()
                .body("name", is(name));
    }

    @Test
    @DisplayName("회의실을 삭제한다. (어드민 권한)")
    void deleteRoom() {
        // given
        String token = TestHelper.loginAsAdmin();

        var id = TestHelper.postWithToken("/rooms", new RoomRequest("회의실"), token)
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("id");
        
        // when
        TestHelper.deleteWithToken("/rooms/" + id, token)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        TestHelper.getWithToken("/rooms", token)
                .then()
                .body("$", hasSize(0));
    }
}
