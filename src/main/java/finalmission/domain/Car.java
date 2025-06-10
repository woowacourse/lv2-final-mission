package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String licensePlate;

    private Long feePerMinute;

    @Builder
    public Car(String name, String licensePlate, Long feePerMinute) {
        this.name = name;
        this.licensePlate = licensePlate;
        this.feePerMinute = feePerMinute;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Car car)) {
            return false;
        }
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
