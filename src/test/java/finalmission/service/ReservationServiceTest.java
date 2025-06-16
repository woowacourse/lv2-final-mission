package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.member.Crew;
import finalmission.domain.member.Coach;
import finalmission.domain.reservation.Reservation;
import finalmission.dto.ReservationRequestDto;
import finalmission.dto.ReservationResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationService reservationService;

    private Crew crew1;
    private Crew crew2;
    private Crew crew3;
    private Coach coach1;
    private Coach coach2;
    private Coach coach3;

    @BeforeEach
    void init() {
        initData();
    }

    @AfterEach
    void clear() {
        jdbcTemplate.execute("DELETE FROM RESERVATION");
        jdbcTemplate.execute("DELETE FROM CREW");
        jdbcTemplate.execute("DELETE FROM COACH");
        jdbcTemplate.execute("DELETE FROM RESERVATION_TIME");
    }

    @Test
    @DisplayName("예약을 저장할 수 있어야 한다.")
    void save_reservation() {

        // given
        ReservationRequestDto requestDto = new ReservationRequestDto(
            1L, 1L, LocalDate.now().plusDays(1)
        );

        // when
        Reservation save = reservationService.save(requestDto, crew1);

        // then
        assertThat(save.getCrew().getName()).isEqualTo("젠슨");
        assertThat(save.getCoach().getName()).isEqualTo("솔라");
    }

    @Test
    @DisplayName("예약을 크루ID에 맞게 조회할 수 있어야 한다.")
    void find_all_reservations_by_crew_id() {

        // given
        ReservationRequestDto requestDto1 = new ReservationRequestDto(
            1L, 1L, LocalDate.now().plusDays(1)
        );
        ReservationRequestDto requestDto2 = new ReservationRequestDto(
            2L, 1L, LocalDate.now().plusDays(2)
        );

        ReservationRequestDto requestDto3 = new ReservationRequestDto(
            3L, 1L, LocalDate.now().plusDays(3)
        );

        reservationService.save(requestDto1, crew1);
        reservationService.save(requestDto2, crew1);
        reservationService.save(requestDto3, crew2);

        // when
        List<ReservationResponse> reservationResponses = reservationService.getAllReservationsFromCrewId(
            1L, crew1);

        // then
        assertThat(reservationResponses.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("예약을 코치ID에 맞게 조회할 수 있어야 한다.")
    void find_all_reservations_by_coach_id() {

        // given
        ReservationRequestDto requestDto1 = new ReservationRequestDto(
            1L, 1L, LocalDate.now().plusDays(1)
        );
        ReservationRequestDto requestDto2 = new ReservationRequestDto(
            1L, 1L, LocalDate.now().plusDays(2)
        );

        ReservationRequestDto requestDto3 = new ReservationRequestDto(
            1L, 1L, LocalDate.now().plusDays(3)
        );

        reservationService.save(requestDto1, crew1);
        reservationService.save(requestDto2, crew2);
        reservationService.save(requestDto3, crew3);

        // when
        List<ReservationResponse> reservationResponses = reservationService.getAllReservationsFromCoachId(
            1L, coach1);

        // then
        assertThat(reservationResponses.size()).isEqualTo(3);
    }

    private void initData() {
        jdbcTemplate.execute("INSERT INTO CREW(id, name, email) VALUES(1L, '젠슨','a@com')");
        jdbcTemplate.execute("INSERT INTO CREW(id,name, email) VALUES(2L, '포포','b@com')");
        jdbcTemplate.execute("INSERT INTO CREW(id, name, email) VALUES(3L, '가이온','c@com')");
        jdbcTemplate.execute("INSERT INTO RESERVATION_TIME(id, start_at) VALUES(1L, '10:00')");
        jdbcTemplate.execute("INSERT INTO RESERVATION_TIME(id, start_at) VALUES(2L, '11:00')");
        jdbcTemplate.execute("INSERT INTO COACH(id, name, email) VALUES(1L, '솔라','a1@com')");
        jdbcTemplate.execute("INSERT INTO COACH(id, name, email) VALUES(2L, '리사','a2@com')");
        jdbcTemplate.execute("INSERT INTO COACH(id, name, email) VALUES(3L, '네오','a3@com')");

        crew1 = new Crew(1L, "젠슨", "a@com");
        crew2 = new Crew(2L, "포포", "b@com");
        crew3 = new Crew(3L, "가이온", "c@com");
        coach1 = new Coach(1L, "솔라", "a1@com");
        coach2 = new Coach(2L, "리사", "a2@com");
        coach3 = new Coach(3L, "네오", "a3@com");
    }
}
