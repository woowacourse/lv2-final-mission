package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

class RefrigeratorTest {

    @Test
    void 몬스터_에너지_드링크를_채워_넣을_수_있다() {
        //given
        Refrigerator refrigerator = new Refrigerator("우테코 냉장고");
        new MonsterEnergyStock(MonsterEnergy.ULTRA, 3, refrigerator);

        //when
        refrigerator.increment(MonsterEnergy.ULTRA, 1);

        //then
        assertThat(refrigerator.getMonsterEnergyStocks())
                .extracting("monsterEnergy", "stock")
                .containsExactly(Tuple.tuple(
                        MonsterEnergy.ULTRA, 4
                ));
    }

    @Test
    void 몬스터_에너지_드링크를_꺼낼_수_있다() {
        //given
        Refrigerator refrigerator = new Refrigerator("우테코 냉장고");
        new MonsterEnergyStock(MonsterEnergy.ULTRA, 3, refrigerator);

        //when
        refrigerator.decrease(MonsterEnergy.ULTRA, 1);

        //then
        assertThat(refrigerator.getMonsterEnergyStocks())
                .extracting("monsterEnergy", "stock")
                .containsExactly(Tuple.tuple(
                        MonsterEnergy.ULTRA, 2
                ));
    }

    @Test
    void 몬스터_에너지_드링크를_꺼낼때_해당하는_몬스터_에너지_드링크가_없다면_예외가_발생한다() {
        //given
        Refrigerator refrigerator = new Refrigerator("우테코 냉장고");

        // when & then
        assertThatThrownBy(() -> refrigerator.decrease(MonsterEnergy.ULTRA, 4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 몬스터 에너지 드링크가 없습니다.");
    }

    @Test
    void 몬스터_에너지_드링크를_꺼낼때_해당하는_몬스터_에너지_드링크의_재고가_없다면_예외가_발생한다() {
        //given
        Refrigerator refrigerator = new Refrigerator("우테코 냉장고");
        new MonsterEnergyStock(MonsterEnergy.ULTRA, 3, refrigerator);

        // when & then
        assertThatThrownBy(() -> refrigerator.decrease(MonsterEnergy.ULTRA, 4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다.");
    }
}
