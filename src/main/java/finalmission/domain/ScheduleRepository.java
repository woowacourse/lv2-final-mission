package finalmission.domain;

public interface ScheduleRepository {

    Ticket save(final Schedule schedule);

    Schedule findByTicket(final Ticket ticket);
}
