package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refrigerator {

    @Id
    private String id;

    @OneToMany(mappedBy = "refrigerator")
    private List<MonsterEnergyStock> monsterEnergyStocks = new ArrayList<>();

    public Refrigerator(String id) {
        this.id = id;
    }

    public void increment(MonsterEnergy monsterEnergy, int quantity) {
        for (MonsterEnergyStock monsterEnergyStock : monsterEnergyStocks) {
            if (monsterEnergyStock.isMonsterEnergy(monsterEnergy)) {
                monsterEnergyStock.increment(quantity);
                break;
            }
        }
    }

    public void decrease(MonsterEnergy monsterEnergy, int quantity) {
        boolean isReserved = false;
        for (MonsterEnergyStock monsterEnergyStock : monsterEnergyStocks) {
            if (monsterEnergyStock.isMonsterEnergy(monsterEnergy)) {
                isReserved = true;
                monsterEnergyStock.decrease(quantity);
                break;
            }
        }

        if (!isReserved) {
            throw new IllegalArgumentException("해당하는 몬스터 에너지 드링크가 없습니다.");
        }
    }

    public void addMonsterEnergyStock(MonsterEnergyStock monsterEnergyStock) {
        if (!monsterEnergyStocks.contains(monsterEnergyStock)) {
            monsterEnergyStocks.add(monsterEnergyStock);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Refrigerator that)) {
            return false;
        }

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
