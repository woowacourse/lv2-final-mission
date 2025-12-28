package finalmission.mungPlan.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeSlots {

    @ElementCollection
    @CollectionTable(
            name = "plan_date_time_slots",
            joinColumns = @JoinColumn(name = "plan_date_id")
    )
    private final List<TimeSlot> availableTimes = new ArrayList<>();

    public TimeSlots(List<TimeSlot> timeSlots) {
        availableTimes.addAll(timeSlots);
    }

    public void addTime(TimeSlot timeSlot) {
        availableTimes.add(timeSlot);
    }

    public Optional<TimeSlot> findByStartAt(LocalTime startAt) {
        return availableTimes.stream()
                .filter(planTime -> planTime.isSameStartAt(startAt))
                .findAny();
    }
}
