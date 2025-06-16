package finalmission.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Schedule(
    Room room,
    LocalDate date,
    LocalTime time
) {

    public Schedule {
        validateNonNull(room, date, time);
    }

    private void validateNonNull(
        final Room room,
        final LocalDate date,
        final LocalTime time
    ) {
        if (room == null) {
            throw new IllegalArgumentException("회의실은 필수입니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수입니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }

    public boolean isConflictWith(final Schedule other) {
        if (!this.room.equals(other.room) || !this.date.equals(other.date)) {
            return false;
        }

        final LocalTime thisEndTime = this.time.plusMinutes(59);
        final LocalTime otherEndTime = other.time.plusMinutes(59);

        return !thisEndTime.isBefore(other.time) && !this.time.isAfter(otherEndTime);
    }
}
