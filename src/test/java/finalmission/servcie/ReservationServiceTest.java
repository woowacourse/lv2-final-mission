package finalmission.servcie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.Role;
import finalmission.domain.Seat;
import finalmission.dto.layer.ReservationCreationContent;
import finalmission.dto.layer.ReservationUpdateContent;
import finalmission.dto.response.FindAllReservationByMember;
import finalmission.dto.response.FindAllReservationBySeat;
import finalmission.dto.response.FindReservationById;
import finalmission.exception.BadRequestException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(value = {ReservationService.class})
class ReservationServiceTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ReservationService reservationService;

    @Nested
    @DisplayName("자리에 해당하는 모든 예약을 조회할 수 있다.")
    public class FindAllReservationBySeats {

        @DisplayName("정상적으로 자리에 해당하는 모든 예약을 조회할 수 있다.")
        @Test
        void findAllReservationBySeats() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));
            entityManager.persist(new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1)));
            entityManager.persist(new Reservation(member, seat, "공부2", LocalDate.now().plusDays(2)));
            entityManager.persist(new Reservation(member, seat, "공부3", LocalDate.now().plusDays(3)));

            entityManager.flush();
            entityManager.clear();

            // when
            List<FindAllReservationBySeat> allReservationBySeats =
                    reservationService.findAllReservationBySeats(seat.getId());

            // then
            assertThat(allReservationBySeats).hasSize(3);
        }
    }

    @Nested
    @DisplayName("회원의 모든 예약을 조회할 수 있다.")
    public class CanFindAllReservationByMember {

        @DisplayName("정상적으로 회원의 모든 예약을 조회할 수 있다.")
        @Test
        void findAllReservationByMember() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));
            entityManager.persist(new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1)));
            entityManager.persist(new Reservation(member, seat, "공부2", LocalDate.now().plusDays(2)));
            entityManager.persist(new Reservation(member, seat, "공부3", LocalDate.now().plusDays(3)));

            entityManager.flush();
            entityManager.clear();

            // when
            List<FindAllReservationByMember> allReservationByMember =
                    reservationService.findAllReservationByMember(member.getId());

            // then
            assertThat(allReservationByMember).hasSize(3);
        }
    }

    @Nested
    @DisplayName("자신의 예약의 세부내용을 조회할 수 있다.")
    public class CanFindReservationById {

        @DisplayName("정상적으로 자신의 예약의 세부내용을 조회할 수 있다.")
        @Test
        void findAllReservationByMember() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));
            Reservation reservation = entityManager.persist(
                    new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1)));

            entityManager.flush();
            entityManager.clear();

            // when
            FindReservationById reservationById =
                    reservationService.findById(member.getId(), reservation.getId());

            // then
            assertThat(reservationById.reservationId()).isEqualTo(reservation.getId());
        }

        @DisplayName("다른 사람의 예약의 세부내용을 조회할 수 없다.")
        @Test
        void addReservation() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Member otherMember = entityManager.persist(new Member("test1@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));
            Reservation reservation = entityManager.persist(
                    new Reservation(otherMember, seat, "공부1", LocalDate.now().plusDays(1)));

            entityManager.flush();
            entityManager.clear();

            // when & then
            assertThatThrownBy(() -> reservationService.findById(member.getId(), reservation.getId()))
                    .isInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    @DisplayName("자신의 예약을 추가할 수 있다.")
    public class CanAddReservation {

        @DisplayName("정상적으로 자신의 예약을 추가할 수 있다.")
        @Test
        void canAddReservation() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));

            entityManager.flush();
            entityManager.clear();

            ReservationCreationContent creationContent = new ReservationCreationContent(
                    member.getId(), seat.getId(), "사유", LocalDate.now().plusDays(1));

            // when
            Reservation reservation = reservationService.addReservation(creationContent);

            // then
            assertThat(entityManager.find(Reservation.class, reservation.getId()))
                    .isNotNull();
        }

        @DisplayName("이미 예약이 완료된 경우 예약을 추가할 수 있다.")
        @Test
        void cannotAddAlreadyReserved() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Member otherMember = entityManager.persist(new Member("test1@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));
            Reservation reservation = entityManager.persist(
                    new Reservation(otherMember, seat, "공부1", LocalDate.now().plusDays(1)));

            entityManager.flush();
            entityManager.clear();

            ReservationCreationContent creationContent = new ReservationCreationContent(
                    member.getId(), seat.getId(), "사유", reservation.getDate());

            // when & then
            assertThatThrownBy(() -> reservationService.addReservation(creationContent))
                    .isInstanceOf(BadRequestException.class);
        }

        @DisplayName("과거 날짜의 예약을 추가할 수 있다.")
        @Test
        void cannotAddPastDateReservation() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));

            entityManager.flush();
            entityManager.clear();

            ReservationCreationContent creationContent = new ReservationCreationContent(
                    member.getId(), seat.getId(), "사유", LocalDate.now().minusDays(1));

            // when & then
            assertThatThrownBy(() -> reservationService.addReservation(creationContent))
                    .isInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    @DisplayName("자신의 예약의 부가 내용을 수정할 수 있다.")
    public class UpdateReservationById {

        @DisplayName("정상적으로 자신의 예약의 부가 내용을 수정할 수 있다.")
        @Test
        void updateReservationById() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));
            Reservation reservation = entityManager.persist(
                    new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1)));

            entityManager.flush();
            entityManager.clear();

            ReservationUpdateContent updateContent = new ReservationUpdateContent(reservation.getId(), "새로운 사유");

            // when
            reservationService.updateReservationById(member.getId(), updateContent);

            // then
            Reservation updatedReservation = entityManager.find(Reservation.class, reservation.getId());
            assertThat(updatedReservation.getReason())
                    .isEqualTo(updateContent.reason());
        }

        @DisplayName("다른 사람의 예약을 수정할 수 없다.")
        @Test
        void cannotUpdateOtherMemberReservation() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Member otherMember = entityManager.persist(new Member("test1@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));
            Reservation reservation = entityManager.persist(
                    new Reservation(otherMember, seat, "공부1", LocalDate.now().plusDays(1)));

            entityManager.flush();
            entityManager.clear();

            ReservationUpdateContent updateContent = new ReservationUpdateContent(reservation.getId(), "새로운 사유");

            // when & then
            assertThatThrownBy(() -> reservationService.updateReservationById(member.getId(), updateContent))
                    .isInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    @DisplayName("자신의 예약을 삭제할 수 있다.")
    public class DeleteReservation {

        @DisplayName("정상적으로 자신의 예약을 삭제할 수 있다.")
        @Test
        void deleteReservation() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));
            Reservation reservation = entityManager.persist(
                    new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1)));

            // when
            reservationService.deleteReservation(member.getId(), reservation.getId());

            // then
            assertThat(entityManager.find(Reservation.class, reservation.getId())).isNull();
        }

        @DisplayName("다른 사람의 예약을 삭제할 수 없다.")
        @Test
        void cannotDeleteReservation() {
            // given
            Member member = entityManager.persist(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Member otherMember = entityManager.persist(new Member("test1@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = entityManager.persist(new Seat("ABC"));
            Reservation reservation = entityManager.persist(
                    new Reservation(otherMember, seat, "공부1", LocalDate.now().plusDays(1)));

            // when & then
            assertThatThrownBy(() -> reservationService.deleteReservation(member.getId(), reservation.getId()))
                    .isInstanceOf(BadRequestException.class);
        }
    }
}
