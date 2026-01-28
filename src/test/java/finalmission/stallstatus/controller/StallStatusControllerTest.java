package finalmission.stallstatus.controller;

import finalmission.exception.NotFoundException;
import finalmission.member.controller.dto.request.LoginRequest;
import finalmission.member.controller.dto.request.SignUpRequest;
import finalmission.member.service.MemberService;
import finalmission.stall.controller.dto.request.StallCreateRequest;
import finalmission.stall.controller.dto.response.StallCreateResponse;
import finalmission.stall.service.StallService;
import finalmission.stallstatus.controller.dto.request.StallStatusCreateRequest;
import finalmission.stallstatus.controller.dto.response.StallStatusCreateResponse;
import finalmission.stallstatus.entity.Status;
import finalmission.stallstatus.service.StallStatusService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StallStatusControllerTest {

    @Autowired
    private StallService stallService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StallStatusService stallStatusService;

    @Test
    void 화장실_사로_상태_생성_요청_API_테스트() {
        String token = getToken("test1234", "1234");

        StallCreateResponse stallCreateResponse = stallService.create(
                new StallCreateRequest("1사로")
        );


        StallStatusCreateRequest request = new StallStatusCreateRequest(stallCreateResponse.id());

        StallStatusCreateResponse response = RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", token)
                .body(request)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallStatusCreateResponse.class);

        assertThat(response.status()).isEqualTo(Status.USING);
    }

    @Test
    void 사로_사용_중인_경우_StallStatus_PENDING_테스트() {
        String user1Token = getToken("test1234", "1234");

        StallCreateResponse stallCreateResponse = stallService.create(
                new StallCreateRequest("1사로")
        );

        StallStatusCreateRequest firstRequest = new StallStatusCreateRequest(stallCreateResponse.id());

        StallStatusCreateResponse firstResponse = RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", user1Token)
                .body(firstRequest)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallStatusCreateResponse.class);

        String user2Token = getToken("test12345", "12345");

        StallStatusCreateRequest secondRequest = new StallStatusCreateRequest(stallCreateResponse.id());

        StallStatusCreateResponse secondResponse = RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", user2Token)
                .body(secondRequest)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallStatusCreateResponse.class);

        assertAll(
                () -> assertThat(firstResponse.status()).isEqualTo(Status.USING),
                () -> assertThat(secondResponse.status()).isEqualTo(Status.PENDING)
        );
    }

    @Test
    void 같은_유저가_중복된_사로_상태_이용시_예외_발생_테스트() {
        String user1Token = getToken("test1234", "1234");

        StallCreateResponse stallCreateResponse = stallService.create(
                new StallCreateRequest("1사로")
        );

        StallStatusCreateRequest firstRequest = new StallStatusCreateRequest(stallCreateResponse.id());

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", user1Token)
                .body(firstRequest)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallStatusCreateResponse.class);

        StallStatusCreateRequest secondRequest = new StallStatusCreateRequest(1L);

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", user1Token)
                .body(secondRequest)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }


    @Test
    void 내_사로_상태가_아닌_정보를_삭제하는_경우_예외_발생() {
        String user1Token = getToken("test1234", "1234");

        StallCreateResponse stallCreateResponse = stallService.create(
                new StallCreateRequest("1사로")
        );

        StallStatusCreateRequest firstRequest = new StallStatusCreateRequest(stallCreateResponse.id());

        StallStatusCreateResponse firstResponse = RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", user1Token)
                .body(firstRequest)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallStatusCreateResponse.class);

        String user2Token = getToken("test12345", "12345");

        RestAssured.given().log().all()
                .cookie("token", user2Token)
                .when().delete(String.format("/status/%s", firstResponse.stallStatusId()))
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 이용중인_사로_상태_삭제_시_대기_중인_사로_상태_변경_테스트() {
        String user1Token = getToken("test1234", "1234");

        StallCreateResponse stallCreateResponse = stallService.create(
                new StallCreateRequest("1사로")
        );

        StallStatusCreateRequest firstRequest = new StallStatusCreateRequest(stallCreateResponse.id());

        StallStatusCreateResponse firstResponse = RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", user1Token)
                .body(firstRequest)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallStatusCreateResponse.class);

        String user2Token = getToken("test12345", "12345");

        StallStatusCreateRequest secondRequest = new StallStatusCreateRequest(stallCreateResponse.id());

        StallStatusCreateResponse secondResponse = RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", user2Token)
                .body(secondRequest)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallStatusCreateResponse.class);

        RestAssured.given().log().all()
                .cookie("token", user1Token)
                .when().delete(String.format("/status/%s", firstResponse.stallStatusId()))
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());


        assertAll(
                () -> assertThat(firstResponse.status()).isEqualTo(Status.USING),
                () -> assertThat(secondResponse.status()).isEqualTo(Status.PENDING),
                () -> assertThatThrownBy(() -> stallStatusService.getStallStatus(firstResponse.stallStatusId())).isInstanceOf(NotFoundException.class),
                () -> assertThat(stallStatusService.getStallStatus(secondResponse.stallStatusId()).status()).isEqualTo(Status.USING)

        );
    }


    @Test
    void 나의_사로_상태_목록_조회_요청_API_테스트() {
        String token = getToken("test1234", "1234");

        StallCreateResponse firstStallCreateResponse = stallService.create(
                new StallCreateRequest("1사로")
        );

        StallStatusCreateRequest firstRequest = new StallStatusCreateRequest(firstStallCreateResponse.id());

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", token)
                .body(firstRequest)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallStatusCreateResponse.class);

        MyStallStatusResponse myStallStatusResponse = RestAssured.given().log().all()
                .cookie("token", token)
                .when().get("/status/mine")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body().as(MyStallStatusResponse.class);


        assertThat(myStallStatusResponse.stallName()).isEqualTo(firstStallCreateResponse.name());
    }


    private String getToken(String nickname, String password) {

        SignUpRequest signUpRequest = new SignUpRequest(nickname, password);
        memberService.signup(signUpRequest);

        LoginRequest loginRequest = new LoginRequest(nickname, password);

        String token = RestAssured.given().log().all()
                .contentType("application/json")
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().cookie("token");
        return token;
    }
}
