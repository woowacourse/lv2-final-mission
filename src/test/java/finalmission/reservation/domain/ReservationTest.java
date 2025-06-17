package finalmission.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.member.domain.Member;
import finalmission.popupstore.domain.PopupStore;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private static Member createMember() {
        return Member.create("이름", "닉네임");
    }

    private static PopupStore createPopupStore() {
        return PopupStore.open(
                "팝업스토어 타이틀",
                "팝업스토어 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                20,
                null
        );
    }

    @Test
    void 예약한다() {
        // given
        Member member = createMember();
        PopupStore popupStore = createPopupStore();
        LocalDateTime reservedAt = LocalDateTime.now();

        // when
        Reservation reservation = Reservation.reserve(
                member,
                popupStore,
                reservedAt,
                false
        );

        // then
        assertThat(reservation.getReserver()).isEqualTo(member);
        assertThat(reservation.getPopupStore()).isEqualTo(popupStore);
        assertThat(reservation.getReservedAt()).isEqualTo(reservedAt);
        assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.ENTERED);
    }
}