package finalmission.domain.schedule;

import finalmission.domain.restaurant.Restaurant;
import finalmission.domain.schedule.detail.ScheduleDetail;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ScheduleDetail detail;

    @ManyToOne
    private Restaurant restaurant;

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public ScheduleDetail getDetail() {
        return detail;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Schedule schedule)) {
            return false;
        }

        return Objects.equals(getId(), schedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
