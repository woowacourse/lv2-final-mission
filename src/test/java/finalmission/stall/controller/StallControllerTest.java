package finalmission.stall.controller;

import finalmission.member.controller.dto.request.LoginRequest;
import finalmission.member.controller.dto.request.SignUpRequest;
import finalmission.member.service.MemberService;
import finalmission.stall.controller.dto.request.StallCreateRequest;
import finalmission.stall.controller.dto.response.StallCreateResponse;
import finalmission.stall.controller.dto.response.StallInfosResponse;
import finalmission.stall.service.StallService;
import finalmission.stallstatus.controller.dto.request.StallStatusCreateRequest;
import finalmission.stallstatus.controller.dto.response.StallStatusCreateResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StallControllerTest {

    @Autowired
    private StallService stallService;

    @Autowired
    private MemberService memberService;

    @Test
    void 화장실_사로_생성_요청_API_테스트() {
        String token = getToken();

        StallCreateRequest request = new StallCreateRequest("1사로");

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", token)
                .body(request)
                .when().post("/stalls")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 화장실_사로_전체_조회_API_테스트() {
        String token = getToken();

        StallCreateRequest request = new StallCreateRequest("1사로");

        StallCreateResponse stallCreateResponse = RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", token)
                .body(request)
                .when().post("/stalls")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallCreateResponse.class);

        StallStatusCreateRequest stallStatusCreateRequest = new StallStatusCreateRequest(stallCreateResponse.id());

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token", token)
                .body(stallStatusCreateRequest)
                .when().post("/status")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body().as(StallStatusCreateResponse.class);

        StallInfosResponse stallInfosResponse = RestAssured.given().log().all()
                .cookie("token", token)
                .when().get("/stalls")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body().as(StallInfosResponse.class);

        assertThat(stallInfosResponse.stallInfos().size()).isEqualTo(1);
        assertThat(stallInfosResponse.stallInfos().getFirst().stallStatusFindResponses().size()).isEqualTo(1);
    }

    @Test
    void 화장싱_사로_삭제_요청_API_테스트() {
        String token = getToken();

        StallCreateRequest request = new StallCreateRequest("1사로");

        StallCreateResponse stallCreateResponse = stallService.create(request);

        RestAssured.given().log().all()
                .cookie("token", token)
                .contentType("application/json")
                .body(request)
                .when().delete(String.format("/stalls/%s", stallCreateResponse.id()))
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        StallInfosResponse stalls = stallService.findStalls();
        assertThat(stalls.stallInfos().size()).isEqualTo(0);
    }

    private String getToken() {
        String nickname = "testUser";
        String password = "1234";

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
