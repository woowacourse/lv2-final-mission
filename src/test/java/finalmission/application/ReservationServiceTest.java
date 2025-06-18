package finalmission.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import finalmission.application.dto.request.CreateReservationRequest;
import finalmission.application.dto.request.UpdateReservationRequest;
import finalmission.application.dto.response.CreateReservationResponse;
import finalmission.application.support.exception.BusinessRuleViolationException;
import finalmission.domain.Email;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.domain.MonsterEnergy;
import finalmission.domain.MonsterEnergyStock;
import finalmission.domain.MonsterEnergyStockRepository;
import finalmission.domain.Refrigerator;
import finalmission.domain.RefrigeratorRepository;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationRepository;
import finalmission.domain.Role;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ReservationServiceTest extends AbstractServiceIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MonsterEnergyStockRepository monsterEnergyStockRepository;

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 특정_몬스터_에너지_드링크를_예약할_수_있다() {
        //given
        Member member = memberRepository.save(Member.create(new Email("user@gmail.com"), "서프", "password", Role.USER));
        Refrigerator refrigerator = new Refrigerator("우테코");
        new MonsterEnergyStock(MonsterEnergy.ULTRA, 10, refrigerator);
        Refrigerator savedRefrigerator = refrigeratorRepository.save(refrigerator);
        CreateReservationRequest createReservationRequest = new CreateReservationRequest(
                MonsterEnergy.ULTRA,
                8,
                LocalDateTime.now().plusDays(3)
        );

        //when
        CreateReservationResponse createReservationResponse = reservationService.reserve(
                member.getId(),
                refrigerator.getId(),
                createReservationRequest);

        //then
        assertThat(reservationRepository.findById(createReservationResponse.reservationId()))
                .isPresent()
                .get()
                .extracting("monsterEnergy", "quantity", "dateTime", "refrigerator", "member")
                .containsExactly(MonsterEnergy.ULTRA, 8, createReservationRequest.dateTime(), refrigerator, member);
        assertThat(savedRefrigerator.getMonsterEnergyStocks())
                .extracting("monsterEnergy", "stock")
                .containsExactly(tuple(MonsterEnergy.ULTRA, 2));
    }

    @Test
    void 사용자는_예약의_재고를_변경할_수_있다() {
        //given
        Member member = memberRepository.save(Member.create(new Email("user@gmail.com"), "서프", "password", Role.USER));
        Refrigerator refrigerator = new Refrigerator("우테코");
        MonsterEnergyStock monsterEnergyStock = new MonsterEnergyStock(MonsterEnergy.ULTRA, 10, refrigerator);
        refrigeratorRepository.save(refrigerator);
        Reservation reservation = reservationRepository.save(
                Reservation.create(
                        MonsterEnergy.ULTRA,
                        8,
                        LocalDateTime.now().plusDays(3),
                        refrigerator,
                        member,
                        LocalDateTime.now()
                )
        );

        //when
        reservationService.update(member.getId(), reservation.getId(), new UpdateReservationRequest(9));

        //then
        assertThat(reservation)
                .extracting("monsterEnergy", "quantity")
                .containsExactly(MonsterEnergy.ULTRA, 9);
        assertThat(monsterEnergyStock.getStock())
                .isEqualTo(1);
    }

    @Test
    void 예약의_재고를_변경할때_예약을_생성한_사용자가_아니라면_예외가_발생한다() {
        //given
        Member member = memberRepository.save(Member.create(new Email("user@gmail.com"), "서프", "password", Role.USER));
        Refrigerator refrigerator = new Refrigerator("우테코");
        new MonsterEnergyStock(MonsterEnergy.ULTRA, 10, refrigerator);
        refrigeratorRepository.save(refrigerator);
        Reservation reservation = reservationRepository.save(
                Reservation.create(
                        MonsterEnergy.ULTRA,
                        8,
                        LocalDateTime.now().plusDays(3),
                        refrigerator,
                        member,
                        LocalDateTime.now()
                )
        );

        //when & then
        assertThatThrownBy(() -> reservationService.update(999L, reservation.getId(), new UpdateReservationRequest(9)))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessage("예약을 생성한 사용자만 수정이 가능합니다.");
    }

    @Test
    void 예약을_생성하지_않은_사용자가_예약을_삭제할때_예외가_발생한다() {
        //given
        Member member = memberRepository.save(Member.create(new Email("user@gmail.com"), "서프", "password", Role.USER));
        Refrigerator refrigerator = new Refrigerator("우테코");
        new MonsterEnergyStock(MonsterEnergy.ULTRA, 10, refrigerator);
        refrigeratorRepository.save(refrigerator);
        Reservation reservation = reservationRepository.save(
                Reservation.create(
                        MonsterEnergy.ULTRA,
                        8,
                        LocalDateTime.now().plusDays(3),
                        refrigerator,
                        member,
                        LocalDateTime.now()
                )
        );

        //when & then
        assertThatThrownBy(() -> reservationService.delete(999L, reservation.getId()))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessage("예약을 생성한 사용자만 삭제가 가능합니다.");
    }
}
