package finalmission.application;

import finalmission.domain.Reservation;
import finalmission.domain.ReservationRepository;
import finalmission.domain.Room;
import finalmission.domain.Schedule;
import finalmission.domain.ScheduleRepository;
import finalmission.domain.Ticket;
import finalmission.dto.ReservationRequest;
import finalmission.dto.ReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;

    // TODO. 외부 API를 이용한 공휴일 검증
    public Ticket saveReservation(final ReservationRequest reservationRequest) {
        final Room room = Room.findByTitle(reservationRequest.room()).orElseThrow(
            () -> new IllegalArgumentException("회의실을 찾을 수 없습니다."));
        final Schedule schedule = new Schedule(
            room,
            reservationRequest.date(),
            reservationRequest.time()
        );
        validateAvailableSchedule(schedule);
        final Ticket savedTicket = scheduleRepository.save(schedule);

        final Reservation reservation = new Reservation(
            savedTicket,
            reservationRequest.crew(),
            reservationRequest.description()
        );
        final Reservation savedReservation = reservationRepository.save(reservation);
        return savedReservation.getTicket();
    }

    private void validateAvailableSchedule(final Schedule schedule) {
        final List<Schedule> schedules = reservationRepository.findAllTickets().stream()
            .map(scheduleRepository::findByTicket)
            .toList();

        if (schedules.stream().anyMatch(savedSchedules ->
            !savedSchedules.isAvailableSchedule(schedule))) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }
    }

    public List<ReservationResponse> findAllReservation() {
        return reservationRepository.findAll().stream()
            .map(reservation -> {
                final Schedule schedule = scheduleRepository.findByTicket(
                    reservation.getTicket());

                return ReservationResponse.of(schedule, reservation);
            }).toList();
    }

    public Schedule findSchedule(final Ticket ticket) {
        return scheduleRepository.findByTicket(ticket);
    }

    public void deleteReservation(final Ticket ticket) {
        reservationRepository.deleteById(ticket);
    }
}
