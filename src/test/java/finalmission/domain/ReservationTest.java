package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void 예약을_생성할때_과거_날짜로_예약을_하면_예외가_발생한다() {
        //given when then
        assertThatThrownBy(() -> Reservation.create(
                MonsterEnergy.ULTRA,
                10,
                LocalDateTime.now().minusDays(1),
                new Refrigerator("우테코"),
                new Member(1L, new Email("fake@fake.com"), "name", "1234", Role.USER),
                LocalDateTime.now()
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("과거로 예약할 수 없습니다.");
    }

    @Test
    void 예약을_생성할때_예약을_한_몬스터_갯수만큼_냉장고에서_꺼낸다() {
        //given
        Refrigerator refrigerator = new Refrigerator("우테코");
        MonsterEnergyStock monsterEnergyStock = new MonsterEnergyStock(MonsterEnergy.ULTRA, 10, refrigerator);

        //when
        Reservation.create(
                MonsterEnergy.ULTRA,
                10,
                LocalDateTime.now(),
                refrigerator,
                new Member(1L, new Email("fake@fake.com"), "name", "1234", Role.USER),
                LocalDateTime.now().minusDays(1)
        );

        //then
        assertThat(monsterEnergyStock.getStock()).isEqualTo(0);
    }
}
