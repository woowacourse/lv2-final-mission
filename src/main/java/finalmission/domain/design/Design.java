package finalmission.domain.design;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class Design {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long designId;
    private String name;
    private int price;
    private int turnaroundTime;

    private Design(Long designId, String name, int price, int turnaroundTime) {
        this.designId = designId;
        this.name = name;
        this.price = price;
        this.turnaroundTime = turnaroundTime;
    }

    public static Design register(String name, int price, int turnaroundTime) {
        return new Design(null, name, price, turnaroundTime);
    }

    public static Design ofExisting(Long designId, String name, int price, int turnaroundTime) {
        return new Design(designId, name, price, turnaroundTime);
    }
}
