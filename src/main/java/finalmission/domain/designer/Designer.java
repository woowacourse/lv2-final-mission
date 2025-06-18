package finalmission.domain.designer;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.DayOfWeek;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Designer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long designerId;
    private String name;
    @Enumerated(EnumType.STRING)
    private DayOfWeek offDay; // 휴무일

    private Designer(Long designerId, String name, DayOfWeek offDay) {
        this.designerId = designerId;
        this.name = name;
        this.offDay = offDay;
    }

    protected Designer() {
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
