package finalmission.waiting.ui;

import static finalmission.fixture.LoginApiFixture.adminLoginAndGetCookies;
import static finalmission.fixture.LoginApiFixture.memberLoginAndGetCookies;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import finalmission.auth.ui.dto.LoginRequest;
import finalmission.email.domain.EmailClient;
import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
import finalmission.fixture.MemberApiFixture;
import finalmission.member.ui.dto.MemberResponse;
import finalmission.member.ui.dto.SignUpRequest;
import finalmission.reservation.ui.dto.CreateReservationRequest;
import finalmission.reservation.ui.dto.CreateReservationTimeRequest;
import finalmission.reservation.ui.dto.ReservationResponse;
import finalmission.reservation.ui.dto.ReservationTimeResponse;
import finalmission.restaurant.ui.dto.CreateRestaurantRequest;
import finalmission.restaurant.ui.dto.RestaurantResponse;
import finalmission.waiting.ui.dto.CreateWaitingRequest;
import finalmission.waiting.ui.dto.WaitingResponse;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayNameGeneration(ReplaceUnderscores.class)
class WaitingRestControllerTest {

    @MockitoBean
    private EmailClient emailClient;

    @BeforeEach
    void setUp() {
        doNothing()
                .when(emailClient)
                .sendEmail(any(SendEmailRequest.class));
    }

    @Test
    void 예약_대기를_추가한다() {
        final LocalDate reservationDate = LocalDate.now().plusDays(1);
        final LocalTime reservationTime = LocalTime.of(12, 0);
        final String restaurantName = "차이나 스토리";
        final String restaurantDescription = "역시 차스입니다.";
        final String restaurantPlace = "서울시 어딘가1";
        final String restaurantPhoneNumber = "02-1234-5678";
        final int maxReservationCount = 20;

        final Map<String, String> adminCookies = adminLoginAndGetCookies();

        // member1 회원가입 후 로그인
        final SignUpRequest signUpRequest1 = MemberApiFixture.signUpRequest1();
        final MemberResponse member1 = MemberApiFixture.signUp(signUpRequest1);
        final Map<String, String> member1Cookies = memberLoginAndGetCookies(
                new LoginRequest(signUpRequest1.email(), signUpRequest1.password())
        );

        // member2 회원가입 후 로그인
        final SignUpRequest signUpRequest2 = MemberApiFixture.signUpRequest2();
        final MemberResponse member2 = MemberApiFixture.signUp(signUpRequest2);
        final Map<String, String> member2Cookies = memberLoginAndGetCookies(
                new LoginRequest(signUpRequest2.email(), signUpRequest2.password())
        );

        // member3 회원 가입 후 로그인
        final SignUpRequest signUpRequest3 = MemberApiFixture.signUpRequest3();
        final MemberResponse member3 = MemberApiFixture.signUp(signUpRequest3);
        final Map<String, String> member3Cookies = memberLoginAndGetCookies(
                new LoginRequest(signUpRequest3.email(), signUpRequest3.password())
        );

        // admin이 예약 시간 생성
        final CreateReservationTimeRequest createReservationTimeRequest = new CreateReservationTimeRequest(
                reservationTime);
        final ReservationTimeResponse timeResponse = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(createReservationTimeRequest)
                .when()
                .post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationTimeResponse.class);

        // admin이 음식점 생성
        final CreateRestaurantRequest createRestaurantRequest = new CreateRestaurantRequest(
                restaurantName,
                restaurantDescription,
                restaurantPlace,
                restaurantPhoneNumber,
                maxReservationCount
        );
        final RestaurantResponse restaurantResponse = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(createRestaurantRequest)
                .when()
                .post("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RestaurantResponse.class);

        // member1이 예약 생성
        final CreateReservationRequest createReservationRequest = new CreateReservationRequest(
                reservationDate,
                timeResponse.id(),
                restaurantResponse.id(),
                member1.id()
        );

        final ReservationResponse reservationResponse = given().log().all()
                .cookies(member1Cookies)
                .contentType(ContentType.JSON)
                .body(createReservationRequest)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationResponse.class);

        // member2가 예약 대기 생성
        final CreateWaitingRequest member2WaitingRequest = new CreateWaitingRequest(
                reservationResponse.date(),
                timeResponse.id(),
                restaurantResponse.id(),
                member2.id()
        );

        final WaitingResponse member2WaitingResponse = given().log().all()
                .cookies(member2Cookies)
                .contentType(ContentType.JSON)
                .body(member2WaitingRequest)
                .when()
                .post("/waitings")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(WaitingResponse.class);

        // member3가 예약 대기 생성
        final CreateWaitingRequest member3WaitingRequest = new CreateWaitingRequest(
                reservationResponse.date(),
                timeResponse.id(),
                restaurantResponse.id(),
                member2.id()
        );

        final WaitingResponse member3WaitingResponse = given().log().all()
                .cookies(member3Cookies)
                .contentType(ContentType.JSON)
                .body(member3WaitingRequest)
                .when()
                .post("/waitings")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(WaitingResponse.class);

        assertThat(member2WaitingResponse).isNotNull();
        assertThat(member3WaitingResponse).isNotNull();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(member2WaitingResponse.id()).isNotNull();
            softAssertions.assertThat(member2WaitingResponse.date()).isEqualTo(reservationDate);
            softAssertions.assertThat(member2WaitingResponse.time().startAt()).isEqualTo(reservationTime);
            softAssertions.assertThat(member2WaitingResponse.restaurant().name()).isEqualTo(restaurantName);
            softAssertions.assertThat(member2WaitingResponse.restaurant().description())
                    .isEqualTo(restaurantDescription);
            softAssertions.assertThat(member2WaitingResponse.restaurant().place()).isEqualTo(restaurantPlace);
            softAssertions.assertThat(member2WaitingResponse.restaurant().phoneNumber())
                    .isEqualTo(restaurantPhoneNumber);
            softAssertions.assertThat(member2WaitingResponse.memberId()).isEqualTo(member2.id());
            softAssertions.assertThat(member2WaitingResponse.memberNickname()).isEqualTo(member2.nickname());

            softAssertions.assertThat(member3WaitingResponse.id()).isNotNull();
            softAssertions.assertThat(member3WaitingResponse.date()).isEqualTo(reservationDate);
            softAssertions.assertThat(member3WaitingResponse.time().startAt()).isEqualTo(reservationTime);
            softAssertions.assertThat(member3WaitingResponse.restaurant().name()).isEqualTo(restaurantName);
            softAssertions.assertThat(member3WaitingResponse.restaurant().description())
                    .isEqualTo(restaurantDescription);
            softAssertions.assertThat(member3WaitingResponse.restaurant().place()).isEqualTo(restaurantPlace);
            softAssertions.assertThat(member3WaitingResponse.restaurant().phoneNumber())
                    .isEqualTo(restaurantPhoneNumber);
            softAssertions.assertThat(member3WaitingResponse.memberId()).isEqualTo(member3.id());
            softAssertions.assertThat(member3WaitingResponse.memberNickname()).isEqualTo(member3.nickname());
        });
        // 예약 성공자에게 예약 성공 메일 1개 보냄
        Mockito.verify(emailClient, Mockito.times(1))
                .sendEmail(any());
    }

    @Test
    void 내_예약_대기를_삭제한다() {
        final LocalDate reservationDate = LocalDate.now().plusDays(1);
        final LocalTime reservationTime = LocalTime.of(12, 0);
        final String restaurantName = "차이나 스토리";
        final String restaurantDescription = "역시 차스입니다.";
        final String restaurantPlace = "서울시 어딘가1";
        final String restaurantPhoneNumber = "02-1234-5678";
        final int maxReservationCount = 20;

        final Map<String, String> adminCookies = adminLoginAndGetCookies();

        // member1 회원가입 후 로그인
        final SignUpRequest signUpRequest1 = MemberApiFixture.signUpRequest1();
        final MemberResponse member1 = MemberApiFixture.signUp(signUpRequest1);
        final Map<String, String> member1Cookies = memberLoginAndGetCookies(
                new LoginRequest(signUpRequest1.email(), signUpRequest1.password())
        );

        // member2 회원가입 후 로그인
        final SignUpRequest signUpRequest2 = MemberApiFixture.signUpRequest2();
        final MemberResponse member2 = MemberApiFixture.signUp(signUpRequest2);
        final Map<String, String> member2Cookies = memberLoginAndGetCookies(
                new LoginRequest(signUpRequest2.email(), signUpRequest2.password())
        );

        // admin이 예약 시간 생성
        final CreateReservationTimeRequest createReservationTimeRequest = new CreateReservationTimeRequest(
                reservationTime);
        final ReservationTimeResponse timeResponse = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(createReservationTimeRequest)
                .when()
                .post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationTimeResponse.class);

        // admin이 음식점 생성
        final CreateRestaurantRequest createRestaurantRequest = new CreateRestaurantRequest(
                restaurantName,
                restaurantDescription,
                restaurantPlace,
                restaurantPhoneNumber,
                maxReservationCount
        );
        final RestaurantResponse restaurantResponse = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(createRestaurantRequest)
                .when()
                .post("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RestaurantResponse.class);

        // member1이 예약 생성
        final CreateReservationRequest createReservationRequest = new CreateReservationRequest(
                reservationDate,
                timeResponse.id(),
                restaurantResponse.id(),
                member1.id()
        );

        final ReservationResponse reservationResponse = given().log().all()
                .cookies(member1Cookies)
                .contentType(ContentType.JSON)
                .body(createReservationRequest)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationResponse.class);

        // member2가 예약 대기 생성
        final CreateWaitingRequest member2WaitingRequest = new CreateWaitingRequest(
                reservationResponse.date(),
                timeResponse.id(),
                restaurantResponse.id(),
                member2.id()
        );

        final WaitingResponse waitingResponse = given().log().all()
                .cookies(member2Cookies)
                .contentType(ContentType.JSON)
                .body(member2WaitingRequest)
                .when()
                .post("/waitings")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(WaitingResponse.class);

        // member2 예약 대기 삭제
        given().log().all()
                .cookies(member2Cookies)
                .contentType(ContentType.JSON)
                .body(member2WaitingRequest)
                .when()
                .delete("/waitings/" + waitingResponse.id())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // member2 예약 대기 목록 조회
        final List<WaitingResponse> member2WaitingResponses = given().log().all()
                .cookies(member2Cookies)
                .contentType(ContentType.JSON)
                .when()
                .get("/waitings/mine")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", WaitingResponse.class);

        assertThat(member2WaitingResponses).isEmpty();
    }

    @Test
    void 내_예약_대기_목록을_조회한다() {
        final LocalDate reservationDate = LocalDate.now().plusDays(1);
        final LocalTime reservationTime = LocalTime.of(12, 0);
        final String restaurantName = "차이나 스토리";
        final String restaurantDescription = "역시 차스입니다.";
        final String restaurantPlace = "서울시 어딘가1";
        final String restaurantPhoneNumber = "02-1234-5678";
        final int maxReservationCount = 20;

        final Map<String, String> adminCookies = adminLoginAndGetCookies();

        // member1 회원가입 후 로그인
        final SignUpRequest signUpRequest1 = MemberApiFixture.signUpRequest1();
        final MemberResponse member1 = MemberApiFixture.signUp(signUpRequest1);
        final Map<String, String> member1Cookies = memberLoginAndGetCookies(
                new LoginRequest(signUpRequest1.email(), signUpRequest1.password())
        );

        // member2 회원가입 후 로그인
        final SignUpRequest signUpRequest2 = MemberApiFixture.signUpRequest2();
        final MemberResponse member2 = MemberApiFixture.signUp(signUpRequest2);
        final Map<String, String> member2Cookies = memberLoginAndGetCookies(
                new LoginRequest(signUpRequest2.email(), signUpRequest2.password())
        );

        // admin이 예약 시간 생성
        final CreateReservationTimeRequest createReservationTimeRequest = new CreateReservationTimeRequest(
                reservationTime);
        final ReservationTimeResponse timeResponse = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(createReservationTimeRequest)
                .when()
                .post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationTimeResponse.class);

        // admin이 음식점 생성
        final CreateRestaurantRequest createRestaurantRequest = new CreateRestaurantRequest(
                restaurantName,
                restaurantDescription,
                restaurantPlace,
                restaurantPhoneNumber,
                maxReservationCount
        );
        final RestaurantResponse restaurantResponse = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(createRestaurantRequest)
                .when()
                .post("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RestaurantResponse.class);

        // member1이 예약 생성
        final CreateReservationRequest createReservationRequest = new CreateReservationRequest(
                reservationDate,
                timeResponse.id(),
                restaurantResponse.id(),
                member1.id()
        );

        final ReservationResponse reservationResponse = given().log().all()
                .cookies(member1Cookies)
                .contentType(ContentType.JSON)
                .body(createReservationRequest)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationResponse.class);

        // member2가 예약 대기 생성
        final CreateWaitingRequest member2WaitingRequest = new CreateWaitingRequest(
                reservationResponse.date(),
                timeResponse.id(),
                restaurantResponse.id(),
                member2.id()
        );

        given().log().all()
                .cookies(member2Cookies)
                .contentType(ContentType.JSON)
                .body(member2WaitingRequest)
                .when()
                .post("/waitings")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // member2 예약 대기 목록 조회
        final List<WaitingResponse> member2WaitingResponses = given().log().all()
                .cookies(member2Cookies)
                .contentType(ContentType.JSON)
                .when()
                .get("/waitings/mine")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", WaitingResponse.class);

        assertThat(member2WaitingResponses).hasSize(1);

        final WaitingResponse member2WaitingResponse = member2WaitingResponses.getFirst();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(member2WaitingResponse.id()).isNotNull();
            softAssertions.assertThat(member2WaitingResponse.date()).isEqualTo(reservationDate);
            softAssertions.assertThat(member2WaitingResponse.time().startAt()).isEqualTo(reservationTime);
            softAssertions.assertThat(member2WaitingResponse.restaurant().name()).isEqualTo(restaurantName);
            softAssertions.assertThat(member2WaitingResponse.restaurant().description())
                    .isEqualTo(restaurantDescription);
            softAssertions.assertThat(member2WaitingResponse.restaurant().place()).isEqualTo(restaurantPlace);
            softAssertions.assertThat(member2WaitingResponse.restaurant().phoneNumber())
                    .isEqualTo(restaurantPhoneNumber);
            softAssertions.assertThat(member2WaitingResponse.memberId()).isEqualTo(member2.id());
            softAssertions.assertThat(member2WaitingResponse.memberNickname()).isEqualTo(member2.nickname());
        });
    }
}
