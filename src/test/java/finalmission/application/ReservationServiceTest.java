package finalmission.application;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.application.dto.request.CreateReservationRequest;
import finalmission.application.dto.response.CreateReservationResponse;
import finalmission.domain.Email;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.domain.MonsterEnergy;
import finalmission.domain.MonsterEnergyStock;
import finalmission.domain.MonsterEnergyStockRepository;
import finalmission.domain.Refrigerator;
import finalmission.domain.RefrigeratorRepository;
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
        Refrigerator refrigerator = refrigeratorRepository.save(new Refrigerator("우테코"));
        monsterEnergyStockRepository.save(new MonsterEnergyStock(MonsterEnergy.ULTRA, 10, refrigerator));
        CreateReservationRequest createReservationRequest = new CreateReservationRequest(
                MonsterEnergy.ULTRA,
                8,
                LocalDateTime.now().plusDays(3)
        );
        entityManager.flush();
        entityManager.clear();

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
        assertThat(monsterEnergyStockRepository.findById(MonsterEnergy.ULTRA))
                .isPresent().get()
                .extracting("stock")
                .isEqualTo(2);
    }
}
