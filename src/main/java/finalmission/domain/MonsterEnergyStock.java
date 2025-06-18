package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonsterEnergyStock {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MonsterEnergy monsterEnergy;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Refrigerator refrigerator;

    public MonsterEnergyStock(MonsterEnergy monsterEnergy, int stock, Refrigerator refrigerator) {
        this.monsterEnergy = monsterEnergy;
        this.stock = stock;
        this.refrigerator = refrigerator;
        refrigerator.addMonsterEnergyStock(this);
    }

    public void decrease(int quantity) {
        if (stock < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }

    public void increment(int quantity) {
        this.stock += quantity;
    }

    public boolean isMonsterEnergy(MonsterEnergy monsterEnergy) {
        return this.monsterEnergy == monsterEnergy;
    }
}
