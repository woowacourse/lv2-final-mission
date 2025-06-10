package finalmission.service;

import finalmission.domain.reservation.Reservation;
import finalmission.domain.reservation.detail.ReservationDetail;
import finalmission.domain.reservation.owner.Owner;
import finalmission.domain.schedule.Schedule;
import finalmission.dto.CreateReservationRequest;
import finalmission.dto.CreateReservationResponse;
import finalmission.repository.ReservationRepository;
import finalmission.repository.ScheduleRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private ScheduleRepository scheduleRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final ScheduleRepository scheduleRepository) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public CreateReservationResponse createReservation(final long scheduleId, final CreateReservationRequest request) {
        final Owner owner = request.toOwner();
        final ReservationDetail reservationDetail = request.toReservationDetail();
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("[404] 스케줄을 찾을 수 없습니다."));
        final List<Reservation> reservations = reservationRepository.findBySchedule(schedule);
        int remainingCapacity = schedule.getRemainingCapacity(reservations);
        if (remainingCapacity < reservationDetail.getNumberOfGuest().getValue()) {
            throw new IllegalArgumentException("[400] 예약 가능 인원을 초과했습니다.");
        }
        final Reservation reservation = reservationRepository.save(new Reservation(owner, reservationDetail, schedule));
        return new CreateReservationResponse(reservation,
                remainingCapacity - reservation.getNumberOfGuest().getValue());
    }
}
