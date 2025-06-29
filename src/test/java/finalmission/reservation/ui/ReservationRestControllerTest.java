package finalmission.reservation.ui;

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
class ReservationRestControllerTest {

    @MockitoBean
    private EmailClient emailClient;

    @BeforeEach
    void setUp() {
        doNothing()
                .when(emailClient)
                .sendEmail(any(SendEmailRequest.class));
    }

    @Test
    void 예약을_생성한다() {
        final LocalDate reservationDate = LocalDate.now().plusDays(1);
        final LocalTime reservationTime = LocalTime.of(12, 0);
        final String restaurantName = "차이나 스토리";
        final String restaurantDescription = "역시 차스입니다.";
        final String restaurantPlace = "서울시 어딘가1";
        final String restaurantPhoneNumber = "02-1234-5678";
        final int maxReservationCount = 20;

        final Map<String, String> adminCookies = adminLoginAndGetCookies();

        // 회원가입 후 로그인
        final SignUpRequest signUpRequest = MemberApiFixture.signUpRequest1();
        final MemberResponse memberResponse = MemberApiFixture.signUp(signUpRequest);
        final Map<String, String> memberCookies = memberLoginAndGetCookies(
                new LoginRequest(signUpRequest.email(), signUpRequest.password())
        );

        // 예약 시간 생성
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

        // 음식점 생성
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

        // 예약 생성
        final CreateReservationRequest createReservationRequest = new CreateReservationRequest(
                reservationDate,
                timeResponse.id(),
                restaurantResponse.id(),
                memberResponse.id()
        );

        final ReservationResponse reservationResponse = given().log().all()
                .cookies(memberCookies)
                .contentType(ContentType.JSON)
                .body(createReservationRequest)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationResponse.class);

        assertThat(reservationResponse).isNotNull();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(reservationResponse.id()).isNotNull();
            softAssertions.assertThat(reservationResponse.restaurantId()).isEqualTo(restaurantResponse.id());
            softAssertions.assertThat(reservationResponse.date()).isEqualTo(reservationDate);
            softAssertions.assertThat(reservationResponse.time()).isEqualTo(reservationTime);
            softAssertions.assertThat(reservationResponse.restaurantName()).isEqualTo(restaurantName);
            softAssertions.assertThat(reservationResponse.description()).isEqualTo(restaurantDescription);
            softAssertions.assertThat(reservationResponse.place()).isEqualTo(restaurantPlace);
            softAssertions.assertThat(reservationResponse.phoneNumber()).isEqualTo(restaurantPhoneNumber);
            softAssertions.assertThat(reservationResponse.memberId()).isEqualTo(memberResponse.id());
        });
        Mockito.verify(emailClient, Mockito.times(1))
                .sendEmail(SendEmailRequest.confirmReservation(memberResponse.email()));
    }

    @Test
    void 내_예약_목록을_조회한다() {
        final LocalDate reservationDate1 = LocalDate.now().plusDays(1);
        final LocalDate reservationDate2 = LocalDate.now().plusDays(2);
        final LocalTime reservationTime1 = LocalTime.of(12, 0);
        final LocalTime reservationTime2 = LocalTime.of(18, 0);
        final String restaurantName1 = "첫 번째 음식점";
        final String restaurantName2 = "두 번째 음식점";

        final Map<String, String> adminCookies = adminLoginAndGetCookies();

        // 회원가입 후 로그인
        final SignUpRequest signUpRequest = MemberApiFixture.signUpRequest2();
        final MemberResponse memberResponse = MemberApiFixture.signUp(signUpRequest);
        final Map<String, String> memberCookies = memberLoginAndGetCookies(
                new LoginRequest(signUpRequest.email(), signUpRequest.password())
        );

        // 예약 시간들 생성
        final CreateReservationTimeRequest timeRequest1 = new CreateReservationTimeRequest(reservationTime1);
        final ReservationTimeResponse timeResponse1 = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(timeRequest1)
                .when()
                .post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationTimeResponse.class);

        final CreateReservationTimeRequest timeRequest2 = new CreateReservationTimeRequest(reservationTime2);
        final ReservationTimeResponse timeResponse2 = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(timeRequest2)
                .when()
                .post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationTimeResponse.class);

        // 음식점들 생성
        final CreateRestaurantRequest restaurantRequest1 = new CreateRestaurantRequest(
                restaurantName1, "첫 번째 음식점 설명", "서울시 강남구", "02-1111-1111", 11
        );
        final RestaurantResponse restaurantResponse1 = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(restaurantRequest1)
                .when()
                .post("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RestaurantResponse.class);

        final CreateRestaurantRequest restaurantRequest2 = new CreateRestaurantRequest(
                restaurantName2, "두 번째 음식점 설명", "서울시 서초구", "02-2222-2222", 22
        );
        final RestaurantResponse restaurantResponse2 = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(restaurantRequest2)
                .when()
                .post("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RestaurantResponse.class);

        // 예약들 생성
        final CreateReservationRequest reservationRequest1 = new CreateReservationRequest(
                reservationDate1, timeResponse1.id(), restaurantResponse1.id(), memberResponse.id()
        );
        given().log().all()
                .cookies(memberCookies)
                .contentType(ContentType.JSON)
                .body(reservationRequest1)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        final CreateReservationRequest reservationRequest2 = new CreateReservationRequest(
                reservationDate2, timeResponse2.id(), restaurantResponse2.id(), memberResponse.id()
        );
        given().log().all()
                .cookies(memberCookies)
                .contentType(ContentType.JSON)
                .body(reservationRequest2)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // 내 예약 목록 조회
        final List<ReservationResponse> responses = given().log().all()
                .cookies(memberCookies)
                .when()
                .get("/reservations/mine")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", ReservationResponse.class);

        assertThat(responses).hasSize(2);

        final ReservationResponse firstReservation = responses.stream()
                .filter(response -> response.restaurantName().equals(restaurantName1))
                .findFirst()
                .orElse(null);
        final ReservationResponse secondReservation = responses.stream()
                .filter(response -> response.restaurantName().equals(restaurantName2))
                .findFirst()
                .orElse(null);

        assertThat(firstReservation).isNotNull();
        assertThat(secondReservation).isNotNull();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(firstReservation.restaurantId()).isEqualTo(restaurantResponse1.id());
            softAssertions.assertThat(firstReservation.date()).isEqualTo(reservationDate1);
            softAssertions.assertThat(firstReservation.time()).isEqualTo(reservationTime1);
            softAssertions.assertThat(firstReservation.restaurantName()).isEqualTo(restaurantName1);
            softAssertions.assertThat(firstReservation.description()).isEqualTo(restaurantResponse1.description());
            softAssertions.assertThat(firstReservation.place()).isEqualTo(restaurantResponse1.place());
            softAssertions.assertThat(firstReservation.phoneNumber()).isEqualTo(restaurantResponse1.phoneNumber());
            softAssertions.assertThat(firstReservation.memberId()).isEqualTo(memberResponse.id());

            softAssertions.assertThat(secondReservation.restaurantId()).isEqualTo(restaurantResponse2.id());
            softAssertions.assertThat(secondReservation.date()).isEqualTo(reservationDate2);
            softAssertions.assertThat(secondReservation.time()).isEqualTo(reservationTime2);
            softAssertions.assertThat(secondReservation.restaurantName()).isEqualTo(restaurantName2);
            softAssertions.assertThat(secondReservation.description()).isEqualTo(restaurantResponse2.description());
            softAssertions.assertThat(secondReservation.place()).isEqualTo(restaurantResponse2.place());
            softAssertions.assertThat(secondReservation.phoneNumber()).isEqualTo(restaurantResponse2.phoneNumber());
            softAssertions.assertThat(secondReservation.memberId()).isEqualTo(memberResponse.id());
        });
    }

    @Test
    void 예약을_삭제한다() {
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

        // member1의 예약 삭제
        given().log().all()
                .cookies(member1Cookies)
                .when()
                .delete("/reservations/" + reservationResponse.restaurantId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // member1의 예약 목록 확인
        final List<ReservationResponse> reservationResponses = given().log().all()
                .cookies(member1Cookies)
                .when()
                .get("/reservations/mine")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", ReservationResponse.class);

        assertThat(reservationResponses).isEmpty();

        // 예약 성공자에게 예약 성공 메일 1개 + 예약 대기자 2명에게 알람 메일 2개 = 총 3개 전송
        Mockito.verify(emailClient, Mockito.times(3))
                .sendEmail(any());
    }
}
