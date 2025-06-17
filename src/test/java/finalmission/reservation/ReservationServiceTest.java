package finalmission.reservation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import finalmission.global.error.exception.ForbiddenException;
import finalmission.member.entity.Member;
import finalmission.member.entity.RoleType;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.dto.request.ReservationUpdateRequest;
import finalmission.reservation.entity.Reservation;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.reservation.service.ReservationService;
import finalmission.room.entity.Room;
import finalmission.room.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ReservationServiceTest {

    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository, memberRepository, roomRepository);
    }

    @Test
    @DisplayName("자신의 예약이 아니라면 예약을 수정하지 못한다.")
    void updateReservation() {
        // given
        var member = memberRepository.save(new Member("미소", "미소", "miso@email.com", "miso", RoleType.USER));
        var room = roomRepository.save(new Room("회의실"));
        var reservation = reservationRepository.save(
                new Reservation(member, room, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), "회의")
        );
        entityManager.persist(member);
        entityManager.persist(room);
        entityManager.persist(reservation);

        var request = new ReservationUpdateRequest("변경 후 목적");

        // when & then
        assertThatThrownBy(() -> reservationService.updateReservation(1L, 2L, request))
                .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @DisplayName("자신의 예약이 아니라면 예약을 삭제하지 못한다.")
    void deleteReservation() {
        // given
        var member = memberRepository.save(new Member("미소", "미소", "miso@email.com", "miso", RoleType.USER));
        var room = roomRepository.save(new Room("회의실"));
        var reservation = reservationRepository.save(
                new Reservation(member, room, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), "회의")
        );
        entityManager.persist(member);
        entityManager.persist(room);
        entityManager.persist(reservation);
        
        // when & then
        assertThatThrownBy(() -> reservationService.deleteReservation(1L, 2L))
                .isInstanceOf(ForbiddenException.class);
    }
}
