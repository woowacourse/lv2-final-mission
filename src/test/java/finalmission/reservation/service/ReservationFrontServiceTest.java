package finalmission.reservation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.common.exception.NotFoundException;
import finalmission.concert.service.detail.ConcertQueryService;
import finalmission.member.auth.vo.MemberInfo;
import finalmission.member.controller.dto.LoginRequest;
import finalmission.member.service.AuthService;
import finalmission.member.service.detail.MemberQueryService;
import finalmission.payment.service.PaymentFrontService;
import finalmission.payment.service.client.KakaoPaymentClient;
import finalmission.payment.service.client.dto.KakaoPayAmount;
import finalmission.payment.service.client.dto.KakaoPaymentApproveResponse;
import finalmission.payment.service.detail.PaymentCommandService;
import finalmission.payment.service.detail.PaymentQueryService;
import finalmission.reservation.controller.dto.ReservationRequest;
import finalmission.reservation.service.detail.ReservationCommandService;
import finalmission.reservation.service.detail.ReservationQueryService;
import finalmission.seat.service.detail.SeatQueryService;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationFrontServiceTest {

    private ReservationFrontService reservationFrontService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuthService authService;

    @Autowired
    private ReservationCommandService reservationCommandService;

    @Autowired
    private ReservationQueryService reservationQueryService;

    @Autowired
    private MemberQueryService memberQueryService;

    @Autowired
    private ConcertQueryService concertQueryService;

    @Autowired
    private SeatQueryService seatQueryService;

    @Autowired
    private PaymentQueryService paymentQueryService;

    @Autowired
    private PaymentCommandService paymentCommandService;

    private PaymentFrontService paymentFrontService;

    private MockWebServer mockWebServer;

    private RestClient restClient;

    private KakaoPaymentClient kakaoPaymentClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        restClient = RestClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        kakaoPaymentClient = new KakaoPaymentClient(restClient);

        paymentFrontService = new PaymentFrontService(
                paymentCommandService,
                paymentQueryService,
                kakaoPaymentClient,
                reservationQueryService,
                seatQueryService,
                concertQueryService
        );

        reservationFrontService = new ReservationFrontService(
                reservationCommandService,
                reservationQueryService,
                memberQueryService,
                concertQueryService,
                seatQueryService,
                paymentFrontService
        );

        jdbcTemplate.update("INSERT INTO member (name, email, password, role) VALUES ('시소', 'siso@gmail.com', '1234', 'MEMBER')");
        jdbcTemplate.update("INSERT INTO member (name, email, password, role) VALUES ('솔라', 'solar@gmail.com', '1234', 'ADMIN')");
        jdbcTemplate.update("INSERT INTO venue (name, address) VALUES ('올림픽공원 KSPO 돔', '서울특별시 송파구 올림픽로 424')");
        jdbcTemplate.update("INSERT INTO concert (title, artist, concert_date, venue_id, price, description) VALUES ('아이유 콘서트', '아이유', '2025-07-20 19:00:00', 1, 120000, '아이유의 2025년 단독 콘서트')");
        jdbcTemplate.update("INSERT INTO seat (seat_number, venue_id) VALUES (1, 1)");
        jdbcTemplate.update("INSERT INTO seat (seat_number, venue_id) VALUES (2, 1)");
        jdbcTemplate.update("INSERT INTO seat (seat_number, venue_id) VALUES (3, 1)");
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void 예약을_생성한다() throws JsonProcessingException {
        // Given
        final KakaoPaymentApproveResponse response = new KakaoPaymentApproveResponse(
                "aid",
                "temp_tid",
                "temp_cid",
                "status",
                "partner_order_id",
                "partner_user_id",
                "payment_method",
                new KakaoPayAmount(
                        10000,
                        0,
                        0,
                        0,
                        0,
                        0
                ),
                null,
                null,
                "item",
                "code",
                "2024",
                "2025",
                "temp",
                "payload"
        );

        final String expectedResponse = objectMapper.writeValueAsString(response);

        mockWebServer.enqueue(new MockResponse()
                .setBody(expectedResponse)
                .addHeader("Content-Type", "application/json"));

        // When & Then
        assertThat(reservationFrontService.create(1L, new ReservationRequest(1L, 2L, "temp_tid", "temp_pg_token"))).isNotNull();
    }

    @Test
    void 예약을_삭제한다() {
        // Given
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json"));

        jdbcTemplate.update("INSERT INTO reservation (member_id, concert_id, seat_id) VALUES (1, 1, 2)");
        jdbcTemplate.update("INSERT INTO payment (tid, amount, reservation_id) VALUES ('t123', 100000, 1)");

        final String token = authService.login(new LoginRequest("siso@gmail.com", "1234"));
        final MemberInfo memberInfo = authService.get(token);

        // When
        reservationFrontService.delete(memberInfo, 1L);

        // Then
        assertThatThrownBy(() -> reservationFrontService.get(1L))
                .isInstanceOf(NotFoundException.class);
    }
}
