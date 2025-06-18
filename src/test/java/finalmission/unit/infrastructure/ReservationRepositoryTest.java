package finalmission.unit.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.Guest;
import finalmission.domain.Member;
import finalmission.domain.Price;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDateTime;
import finalmission.infrastructure.MemberRepository;
import finalmission.infrastructure.ReservationDateTimeRepository;
import finalmission.infrastructure.ReservationRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DataJpaTest
@Sql(value =
        {
                "/sql/member.sql",
                "/sql/reservationDateTime.sql",
        },executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReservationDateTimeRepository reservationDateTimeRepository;

    private List<ReservationDateTime> dateTimes;
    private List<Member> members;

    private Member member1;
    private Member member2;

    private ReservationDateTime reservationDateTime1;
    private ReservationDateTime reservationDateTime2;

    @BeforeEach
    void setUp() {
        dateTimes = reservationDateTimeRepository.findAll();
        members = memberRepository.findAll();

        member1 = members.get(0);
        member2 = members.get(1);

        reservationDateTime1 = dateTimes.get(0);
        reservationDateTime2 = dateTimes.get(1);
        reservationRepository.save(
                Reservation.createWithoutId(reservationDateTime1, member1, new Guest(10), Price.WEEKDAY));
    }

    @Test
    void 예약_저장() {
        //given
        Reservation reservation = Reservation.createWithoutId(reservationDateTime2, member2, new Guest(5),
                Price.WEEKDAY);

        //when & then
        reservationRepository.save(reservation);
        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);
    }

    @Test
    void id로_예약_조회() {
        //given
        Reservation savedReservation = Reservation.createWithoutId(reservationDateTime2, member2, new Guest(5),
                Price.WEEKDAY);

        //when & then
        reservationRepository.save(savedReservation);
        Optional<Reservation> reservation = reservationRepository.findById(savedReservation.getId());

        SoftAssertions soft = new SoftAssertions();

        soft.assertThat(reservation).isPresent();
        soft.assertThat(reservation.get().isReservedBy(member2.getId())).isTrue();
        soft.assertAll();
    }

    @Test
    void id로_예약_삭제() {
        //given
        Reservation savedReservation = reservationRepository.save(
                Reservation.createWithoutId(reservationDateTime2, member2, new Guest(5), Price.WEEKDAY));

        //when & then
        reservationRepository.deleteById(savedReservation.getId());
        Optional<Reservation> reservation = reservationRepository.findById(savedReservation.getId());

        assertThat(reservation).isEmpty();
    }
}
