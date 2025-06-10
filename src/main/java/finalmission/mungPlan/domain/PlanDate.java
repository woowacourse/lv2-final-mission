package finalmission.mungPlan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "plan_dates")
public class PlanDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_date_id")
    private Long id;

    private LocalDate date;

    @Embedded
    private TimeSlots timeSlots;

    public static PlanDate createNew(LocalDate date, TimeSlots timeSlots) {
        return new PlanDate(null, date, timeSlots);
    }
}
