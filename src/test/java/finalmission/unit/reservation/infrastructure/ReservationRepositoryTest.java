package finalmission.unit.reservation.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.infrastructure.ReservationRepository;
import finalmission.toilet.domain.Toilet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ReservationRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void 예약을_저장한다() {
        // given
        Member member = new Member("email@example.com", "nickname", "password");
        entityManager.persist(member);
        Toilet toilet = new Toilet("position");
        entityManager.persist(toilet);
        LocalDate date = LocalDate.now().plusDays(1);
        Reservation reservation = new Reservation(date, LocalTime.of(8, 0), LocalTime.of(9, 0), member, toilet);
        // when
        Reservation savedReservation = reservationRepository.save(reservation);
        // then
        Reservation foundReservation = entityManager.find(Reservation.class, savedReservation.getId());
        assertThat(foundReservation).isNotNull();
        assertThat(foundReservation.getDate()).isEqualTo(date);
        assertThat(foundReservation.getStartAt()).isEqualTo(LocalTime.of(8, 0));
        assertThat(foundReservation.getEndAt()).isEqualTo(LocalTime.of(9, 0));
        assertThat(foundReservation.getMember().getId()).isEqualTo(member.getId());
        assertThat(foundReservation.getToilet().getId()).isEqualTo(toilet.getId());
    }

    @Test
    void 회원ID로_예약을_조회한다() {
        // given
        Member member = new Member("email@example.com", "nickname", "password");
        entityManager.persist(member);
        Toilet toilet = new Toilet("position");
        entityManager.persist(toilet);
        LocalDate date = LocalDate.now().plusDays(1);
        Reservation reservation1 = new Reservation(date, LocalTime.of(9, 0), LocalTime.of(10, 0), member, toilet);
        entityManager.persist(reservation1);

        Member anotherMember = new Member("another@example.com", "another", "password");
        entityManager.persist(anotherMember);
        Reservation reservation2 = new Reservation(date, LocalTime.of(10, 0), LocalTime.of(11, 0), anotherMember,
                toilet);
        entityManager.persist(reservation2);
        // when
        List<Reservation> reservations = reservationRepository.findByMemberId(member.getId());
        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getMember().getId()).isEqualTo(member.getId());
        assertThat(reservations.get(0).getDate()).isEqualTo(date);
        assertThat(reservations.get(0).getStartAt()).isEqualTo(LocalTime.of(9, 0));
        assertThat(reservations.get(0).getEndAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void ID로_예약을_조회한다() {
        // given
        Member member = new Member("email@example.com", "nickname", "password");
        entityManager.persist(member);
        Toilet toilet = new Toilet("position");
        entityManager.persist(toilet);
        LocalDate date = LocalDate.now().plusDays(1);
        Reservation reservation = new Reservation(date, LocalTime.of(9, 0), LocalTime.of(10, 0), member, toilet);
        entityManager.persist(reservation);
        // when
        Optional<Reservation> foundReservation = reservationRepository.findById(reservation.getId());
        // then
        assertThat(foundReservation).isPresent();
        assertThat(foundReservation.get().getDate()).isEqualTo(date);
        assertThat(foundReservation.get().getStartAt()).isEqualTo(LocalTime.of(9, 0));
        assertThat(foundReservation.get().getEndAt()).isEqualTo(LocalTime.of(10, 0));
        assertThat(foundReservation.get().getMember().getId()).isEqualTo(member.getId());
        assertThat(foundReservation.get().getToilet().getId()).isEqualTo(toilet.getId());
    }

    @Test
    void ID가_존재하지_않으면_빈_Optional을_반환한다() {
        // when
        Optional<Reservation> foundReservation = reservationRepository.findById(999L);
        // then
        assertThat(foundReservation).isEmpty();
    }

    @Test
    void ID로_예약을_삭제한다() {
        // given
        Member member = new Member("email@example.com", "nickname", "password");
        entityManager.persist(member);
        Toilet toilet = new Toilet("position");
        entityManager.persist(toilet);
        LocalDate date = LocalDate.now().plusDays(1);
        Reservation reservation = new Reservation(date, LocalTime.of(9, 0), LocalTime.of(10, 0), member, toilet);
        entityManager.persist(reservation);
        // when
        reservationRepository.deleteById(reservation.getId());
        // then
        Reservation foundReservation = entityManager.find(Reservation.class, reservation.getId());
        assertThat(foundReservation).isNull();
    }

    @Test
    void 화장실ID와_날짜로_예약을_조회한다() {
        // given
        Member member = new Member("email@example.com", "nickname", "password");
        entityManager.persist(member);
        Toilet toilet1 = new Toilet("position1");
        entityManager.persist(toilet1);
        Toilet toilet2 = new Toilet("position2");
        entityManager.persist(toilet2);
        LocalDate date = LocalDate.now().plusDays(1);
        Reservation reservation1 = new Reservation(date, LocalTime.of(9, 0), LocalTime.of(10, 0), member, toilet1);
        entityManager.persist(reservation1);
        Reservation reservation2 = new Reservation(date, LocalTime.of(10, 0), LocalTime.of(11, 0), member, toilet2);
        entityManager.persist(reservation2);
        Reservation reservation3 = new Reservation(date.plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0), member,
                toilet1);
        entityManager.persist(reservation3);
        // when
        List<Reservation> reservations = reservationRepository.findByToiletIdAndDate(toilet1.getId(), date);

        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getToilet().getId()).isEqualTo(toilet1.getId());
        assertThat(reservations.get(0).getDate()).isEqualTo(date);
    }
}