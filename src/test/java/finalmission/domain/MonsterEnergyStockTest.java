package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MonsterEnergyStockTest {

    @Test
    void 수량을_감소할_수_있다() {
        //given
        MonsterEnergyStock monsterEnergyStock = new MonsterEnergyStock(
                MonsterEnergy.ULTRA,
                3,
                new Refrigerator("우테코 냉장고")
        );

        //when
        monsterEnergyStock.decrease(3);

        //then
        assertThat(monsterEnergyStock.getStock()).isEqualTo(0);
    }

    @Test
    void 감소하는_수량만큼_충분한_수량이_없다면_예외가_발생한다() {
        //given
        MonsterEnergyStock monsterEnergyStock = new MonsterEnergyStock(
                MonsterEnergy.ULTRA,
                3,
                new Refrigerator("우테코 냉장고")
        );

        //when & then
        assertThatThrownBy(() -> monsterEnergyStock.decrease(4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다.");
    }

    @Test
    void 재고를_증가시킬_수_있다() {
        //given
        MonsterEnergyStock monsterEnergyStock = new MonsterEnergyStock(
                MonsterEnergy.ULTRA,
                3,
                new Refrigerator("우테코 냉장고")
        );

        //when
        monsterEnergyStock.increment(3);

        //then
        assertThat(monsterEnergyStock.getStock()).isEqualTo(6);
    }

}
