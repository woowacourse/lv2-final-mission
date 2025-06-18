package finalmission.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import finalmission.controller.dto.RoomCreateRequest;
import finalmission.domain.Member;
import finalmission.domain.Room;
import finalmission.domain.RoomMember;
import finalmission.domain.vo.LolName;
import finalmission.repository.MemberRepository;
import finalmission.repository.RoomMemberRepository;
import finalmission.repository.RoomRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoomApiTest {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final MemberRepository memberRepository;

    private Member member1;
    private Member member2;

    @Value("${jwt.secret}")
    private String secretKey;
    private String accessToken;

    @Autowired
    public RoomApiTest(
            final RoomRepository roomRepository,
            final RoomMemberRepository roomMemberRepository,
            final MemberRepository memberRepository
    ) {
        this.roomRepository = roomRepository;
        this.roomMemberRepository = roomMemberRepository;
        this.memberRepository = memberRepository;
    }

    @BeforeEach
    void setUp() {
        member1 = memberRepository.save(new Member(
                new LolName("누신누황", "nunu"),
                "qwe123"
        ));
        member2 = memberRepository.save(new Member(
                new LolName("훌라보노", "KR1"),
                "qwe123"
        ));
        accessToken = Jwts.builder()
                .setSubject(member1.getId().toString())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    @DisplayName("방을 생성한다.")
    @Test
    void create() {
        // given
        final RoomCreateRequest request = new RoomCreateRequest(
                "5대5 내전 구함",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참"
        );

        // when & then
        RestAssured.given().log().all()
                .cookie("token", accessToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/room")
                .then().log().all()
                .statusCode(200);

        assertThat(roomRepository.findAll()).isNotEmpty();
        assertThat(roomMemberRepository.findAll()).isNotEmpty();
    }

    @DisplayName("과거의 날짜로 방을 생성하면 400을 응답한다.")
    @Test
    void createPastDateTime() {
        // given
        final RoomCreateRequest request = new RoomCreateRequest(
                "5대5 내전 구함",
                LocalDate.now().minusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참"
        );

        // when & then
        RestAssured.given().log().all()
                .cookie("token", accessToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/room")
                .then().log().all()
                .statusCode(400);
    }

    @DisplayName("방 정보와 참여 인원들을 조회한다.")
    @Test
    void findById() {
        // given
        final Room room = roomRepository.save(new Room(
                "5대5 내전 구함",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member1
        ));
        roomMemberRepository.save(new RoomMember(room, member1));

        // when & then
        RestAssured.given().log().all()
                .when().get("/room/{id}", room.getId())
                .then().log().all()
                .statusCode(200)
                .body("name", response -> equalTo("5대5 내전 구함"));
    }

    @DisplayName("유저가 참여중인 내전방을 조회한다.")
    @Test
    void findByMemberId() {
        // given
        final Room room1 = roomRepository.save(new Room(
                "5대5 내전 구함1",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member1
        ));
        final Room room2 = roomRepository.save(new Room(
                "5대5 내전 구함2",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member2
        ));
        final Room room3 = roomRepository.save(new Room(
                "5대5 내전 구함3",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member2
        ));

        roomMemberRepository.save(new RoomMember(room1, member1));
        roomMemberRepository.save(new RoomMember(room2, member1));
        roomMemberRepository.save(new RoomMember(room3, member1));

        RestAssured.given().log().all()
                .when().get("/room/member/{id}", member1.getId())
                .then().log().all()
                .statusCode(200)
                .body("[0].name", response -> equalTo("5대5 내전 구함1"))
                .body("[1].name", response -> equalTo("5대5 내전 구함2"))
                .body("[2].name", response -> equalTo("5대5 내전 구함3"));
    }

    @DisplayName("모든 방을 조회한다.")
    @Test
    void findAll() {
        // given
        final Room room = roomRepository.save(new Room(
                "5대5 내전 구함",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member1
        ));
        roomMemberRepository.save(new RoomMember(room, member1));

        // when & then
        RestAssured.given().log().all()
                .when().get("/room")
                .then().log().all()
                .statusCode(200)
                .body("[0].name", response -> equalTo("5대5 내전 구함"));
    }

    @DisplayName("해당 내전방에 참여한다.")
    @Test
    void join() {
        // given
        final Room room = roomRepository.save(new Room(
                "5대5 내전 구함",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member1
        ));
        roomMemberRepository.save(new RoomMember(room, member1));

        // when & then
        RestAssured.given().log().all()
                .param("memberId", 2L)
                .param("roomId", 1L)
                .when().get("/room/join")
                .then().log().all()
                .statusCode(204);

        assertThat(roomMemberRepository.findAll()).hasSize(2);
    }

    @DisplayName("날짜가 지난 내전방에 참여를 요청하면 400을 응답한다.")
    @Test
    void joinAlreadyStart() {
        // given
        final Room room = roomRepository.save(new Room(
                "5대5 내전 구함",
                LocalDate.now().minusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member1
        ));
        roomMemberRepository.save(new RoomMember(room, member1));

        // when & then
        RestAssured.given().log().all()
                .param("memberId", 2L)
                .param("roomId", 1L)
                .when().get("/room/join")
                .then().log().all()
                .statusCode(400);
    }

    @DisplayName("이미 내전방에 참여한 유저가 참여를 요청하면 400을 응답한다.")
    @Test
    void joinAlreadyJoinedMember() {
        // given
        final Room room = roomRepository.save(new Room(
                "5대5 내전 구함",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member1
        ));
        roomMemberRepository.save(new RoomMember(room, member1));

        // when & then
        RestAssured.given().log().all()
                .param("memberId", 1L)
                .param("roomId", 1L)
                .when().get("/room/join")
                .then().log().all()
                .statusCode(400);
    }

    @DisplayName("내전방 참여 예약을 취소한다.")
    @Test
    void leave() {
        // given
        final Room room = roomRepository.save(new Room(
                "5대5 내전 구함",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member1
        ));
        roomMemberRepository.save(new RoomMember(room, member1));
        roomMemberRepository.save(new RoomMember(room, member2));

        // when & then
        RestAssured.given().log().all()
                .param("memberId", 2L)
                .param("roomId", 1L)
                .when().delete("/room/leave")
                .then().log().all()
                .statusCode(204);

        assertThat(roomMemberRepository.findAll()).hasSize(1);
    }

    @DisplayName("이미 게임을 시작한 내전방의 참여 예약을 취소하면 400을 응답한다.")
    @Test
    void leaveAlreadyStart() {
        // given
        final Room room = roomRepository.save(new Room(
                "5대5 내전 구함",
                LocalDate.now().minusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member1
        ));
        roomMemberRepository.save(new RoomMember(room, member1));
        roomMemberRepository.save(new RoomMember(room, member2));

        // when & then
        RestAssured.given().log().all()
                .param("memberId", 2L)
                .param("roomId", 1L)
                .when().delete("/room/leave")
                .then().log().all()
                .statusCode(400);
    }

    @DisplayName("내전방 참가자가 아닌 유저가 내전방 참여 예약을 취소하면 400을 응답한다.")
    @Test
    void leaveNotJoinedMember() {
        // given
        final Room room = roomRepository.save(new Room(
                "5대5 내전 구함",
                LocalDate.now().plusDays(1),
                LocalTime.NOON,
                "5대5 내전 구함, 훌라 필참",
                member1
        ));
        roomMemberRepository.save(new RoomMember(room, member1));

        // when & then
        RestAssured.given().log().all()
                .param("memberId", 2L)
                .param("roomId", 1L)
                .when().delete("/room/leave")
                .then().log().all()
                .statusCode(400);
    }
}
