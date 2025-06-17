package finalmission.reservation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import finalmission.common.CleanUp;
import finalmission.member.application.out.MemberRepository;
import finalmission.member.domain.Member;
import finalmission.popupstore.application.out.PopupStoreRepository;
import finalmission.popupstore.domain.PopupStore;
import finalmission.reservation.application.in.dto.Reserve;
import finalmission.reservation.application.out.MyReservation;
import finalmission.reservation.application.out.dto.MyReservationWaitingCount;
import finalmission.reservation.domain.ReservationStatus;
import finalmission.shopkeeper.application.out.ShopkeeperRepository;
import finalmission.shopkeeper.domain.Shopkeeper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ShopkeeperRepository shopkeeperRepository;
    @Autowired
    private PopupStoreRepository popupStoreRepository;
    @Autowired
    private CleanUp cleanUp;

    @BeforeEach
    void setUp() {
        cleanUp.all();
    }

    private static Shopkeeper createShopkeeper() {
        return Shopkeeper.create("스토어 주인 이름");
    }

    private static PopupStore createPopupStore(final Shopkeeper shopkeeper) {
        return PopupStore.open(
                "팝업 타이틀",
                "팝업 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                10,
                shopkeeper
        );
    }

    private static PopupStore createPopupStore(final Shopkeeper shopkeeper, final int maxEnteredMemberCount) {
        return PopupStore.open(
                "팝업 타이틀",
                "팝업 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                maxEnteredMemberCount,
                shopkeeper
        );
    }

    private static Member createReserver(final String name) {
        return Member.create(name, "예약자 닉네임");
    }

    @DisplayName("예약한다.")
    @Test
    void reserve() {
        // given
        Member reserver = createReserver("예약자");
        memberRepository.save(reserver);
        Shopkeeper shopkeeper = createShopkeeper();
        shopkeeperRepository.save(shopkeeper);
        PopupStore popupStore = createPopupStore(shopkeeper);
        popupStoreRepository.save(popupStore);

        Reserve command = new Reserve(
                reserver.getId(),
                popupStore.getId()
        );

        // when
        assertDoesNotThrow(() -> reservationService.reserve(command));
    }

    @DisplayName("내 대기 순번을 조회한다.")
    @Test
    void getMyWaitingCount() {
        // given
        Member reserver1 = createReserver("에약한 사람");
        memberRepository.save(reserver1);
        Member reserver2 = createReserver("대기하는 사람");
        memberRepository.save(reserver2);
        Shopkeeper shopkeeper = createShopkeeper();
        shopkeeperRepository.save(shopkeeper);
        PopupStore popupStore = createPopupStore(shopkeeper, 1);
        popupStoreRepository.save(popupStore);

        Reserve command = new Reserve(
                reserver1.getId(),
                popupStore.getId()
        );
        reservationService.reserve(command);

        Reserve command2 = new Reserve(
                reserver2.getId(),
                popupStore.getId()
        );
        reservationService.reserve(command2);

        // when
        MyReservationWaitingCount myWaitingCount = reservationService.getMyWaitingCount(
                1L,
                reserver2.getId()
        );

        // then
        assertThat(myWaitingCount.waitingCount()).isEqualTo(1);
    }

    @DisplayName("팝업 스토어를 퇴장하면 대기자가 들어온다.")
    @Test
    void leave() {
        // given
        Member reserver1 = createReserver("입장한 사람");
        memberRepository.save(reserver1);
        Member reserver2 = createReserver("대기하는 사람");
        memberRepository.save(reserver2);
        Shopkeeper shopkeeper = createShopkeeper();
        shopkeeperRepository.save(shopkeeper);
        PopupStore popupStore = createPopupStore(shopkeeper, 1);
        popupStoreRepository.save(popupStore);

        Reserve command = new Reserve(reserver1.getId(), popupStore.getId());
        reservationService.reserve(command);
        Reserve command2 = new Reserve(reserver2.getId(), popupStore.getId());
        reservationService.reserve(command2);

        // when
        reservationService.leave(reserver1.getId(), popupStore.getId());

        // then
        MyReservationWaitingCount myWaitingCount = reservationService.getMyWaitingCount(
                popupStore.getId(), reserver2.getId());
        assertThat(myWaitingCount.waitingCount()).isEqualTo(-1);
    }

    @DisplayName("내 예약을 조회한다.")
    @Test
    void getMyReservation() {
        // given
        Member reserver = createReserver("예약자");
        memberRepository.save(reserver);
        Shopkeeper shopkeeper = createShopkeeper();
        shopkeeperRepository.save(shopkeeper);
        PopupStore popupStore = createPopupStore(shopkeeper);
        popupStoreRepository.save(popupStore);

        Reserve command = new Reserve(
                reserver.getId(),
                popupStore.getId()
        );
        reservationService.reserve(command);

        // when
        MyReservation myReservation = reservationService.getMyReservation(popupStore.getId(), reserver.getId());

        // then
        assertThat(myReservation.reservationId()).isNotNull();
        assertThat(myReservation.popupStoreId()).isNotNull();
        assertThat(myReservation.reservedAt()).isNotNull();
        assertThat(myReservation.title()).isEqualTo(popupStore.getTitle());
        assertThat(myReservation.reservationStatus()).isEqualTo(ReservationStatus.ENTERED);
    }
}