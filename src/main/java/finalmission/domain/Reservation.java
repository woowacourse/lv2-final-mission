package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MonsterEnergy monsterEnergy;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Refrigerator refrigerator;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    public Reservation(Long id,
                       MonsterEnergy monsterEnergy,
                       int quantity,
                       LocalDateTime dateTime,
                       Refrigerator refrigerator,
                       Member member) {
        this.id = id;
        this.monsterEnergy = monsterEnergy;
        this.quantity = quantity;
        this.dateTime = dateTime;
        this.refrigerator = refrigerator;
        this.member = member;
    }

    public static Reservation create(MonsterEnergy monsterEnergy,
                                     int quantity,
                                     LocalDateTime dateTime,
                                     Refrigerator refrigerator,
                                     Member member,
                                     LocalDateTime currentDateTime) {
        if (dateTime.isBefore(currentDateTime)) {
            throw new IllegalArgumentException("과거로 예약할 수 없습니다.");
        }
        refrigerator.decrease(monsterEnergy, quantity);
        return new Reservation(null, monsterEnergy, quantity, dateTime, refrigerator, member);
    }

    public void updateQuantity(int quantity) {
        refrigerator.increment(monsterEnergy, this.quantity);
        refrigerator.decrease(monsterEnergy, quantity);
        this.quantity = quantity;
    }

    public boolean isMemberId(Long memberId) {
        return member.getId().equals(memberId);
    }
}
