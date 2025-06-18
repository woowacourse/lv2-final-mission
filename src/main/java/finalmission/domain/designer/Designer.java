package finalmission.domain.designer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.DayOfWeek;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class Designer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long designerId;
    private String name;
    private DayOfWeek offDay; // 휴무일

    private Designer(Long designerId, String name, DayOfWeek offDay) {
        this.designerId = designerId;
        this.name = name;
        this.offDay = offDay;
    }

    public static Designer register(String name, DayOfWeek offDay) {
        return new Designer(null, name, offDay);
    }

    public static Designer ofExisting(Long designerId, String name, DayOfWeek offDay) {
        return new Designer(designerId, name, offDay);
    }

    public boolean isOffDay(final DayOfWeek dayOfWeek) {
        return offDay.equals(dayOfWeek);
    }

    public Long getDesignerId() {
        return designerId;
    }

    public String getName() {
        return name;
    }

    public DayOfWeek getOffDay() {
        return offDay;
    }
}
