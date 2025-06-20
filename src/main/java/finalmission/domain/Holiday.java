package finalmission.domain;

import finalmission.global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holiday extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    private String name;

    private boolean isHoliday;

    public Holiday(final LocalDate date, final String name, final boolean isHoliday) {
        this.date = date;
        this.name = name;
        this.isHoliday = isHoliday;
    }
}
