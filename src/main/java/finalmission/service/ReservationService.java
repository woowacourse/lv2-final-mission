package finalmission.service;

import finalmission.client.RandomNicknameGenerator;
import finalmission.domain.reservation.Reservation;
import finalmission.domain.reservation.detail.ReservationDetail;
import finalmission.domain.reservation.owner.Owner;
import finalmission.domain.schedule.Schedule;
import finalmission.dto.reservation.CreateReservationRequest;
import finalmission.dto.reservation.CreateReservationResponse;
import finalmission.repository.ReservationRepository;
import finalmission.repository.ScheduleRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private ScheduleRepository scheduleRepository;
    private RandomNicknameGenerator randomNicknameGenerator;

    public ReservationService(final ReservationRepository reservationRepository,
                              final ScheduleRepository scheduleRepository,
                              final RandomNicknameGenerator randomNicknameGenerator) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
        this.randomNicknameGenerator = randomNicknameGenerator;
    }

    public CreateReservationResponse createReservation(final long scheduleId, final CreateReservationRequest request) {
        final Owner owner = request.toOwnerWithRandomNickname(randomNicknameGenerator.generate());
        final ReservationDetail reservationDetail = request.toReservationDetail();
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("[404] 스케줄을 찾을 수 없습니다."));

        final List<Reservation> reservations = reservationRepository.findBySchedule(schedule); // TODO 일급 컬렉션
        schedule.validateNewReservationNumberOfGuest(reservations, reservationDetail.getNumberOfGuest());

        final Reservation reservation = reservationRepository.save(new Reservation(owner, reservationDetail, schedule));
        reservations.add(reservation);
        return new CreateReservationResponse(reservation, schedule.calculateRemainingCapacity(reservations));
    }
}
