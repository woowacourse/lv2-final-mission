package finalmission.domain;

import java.util.Optional;

public interface ScheduleRepository {

    Ticket save(final Schedule schedule);

    Optional<Schedule> findById(final Ticket ticket);
}
