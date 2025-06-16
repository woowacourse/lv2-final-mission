package finalmission.application;

import finalmission.domain.AnniversaryRepository;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDetails;
import finalmission.domain.ReservationRepository;
import finalmission.domain.ReservationStatus;
import finalmission.domain.Room;
import finalmission.domain.Schedule;
import finalmission.domain.ScheduleRepository;
import finalmission.domain.Ticket;
import finalmission.presentation.dto.ReservationRequest;
import finalmission.presentation.dto.ReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final AnniversaryRepository anniversaryRepository;

    @Transactional
    public Ticket saveReservation(final ReservationRequest reservationRequest) {
        final Room room = Room.findByTitle(reservationRequest.room()).orElseThrow(
            () -> new IllegalArgumentException("회의실을 찾을 수 없습니다."));
        final Schedule schedule = new Schedule(room, reservationRequest.date(), reservationRequest.time());

        validateAvailableSchedule(schedule);
        final Ticket savedTicket = scheduleRepository.save(schedule);

        final Reservation reservation = new Reservation(
            savedTicket, new ReservationDetails(reservationRequest.crew(), reservationRequest.description()));
        final Reservation savedReservation = reservationRepository.save(reservation);

        return savedReservation.getTicket();
    }

    private void validateAvailableSchedule(final Schedule schedule) {    // TODO. 트랜잭션 외부로 분리
        final List<Schedule> savedSchedules = reservationRepository.findAll().stream()
            .filter(savedReservation -> savedReservation.getStatus() == ReservationStatus.CONFIRMED)
            .map(Reservation::getTicket)
            .map(ticket -> scheduleRepository.findById(ticket).orElseThrow(
                () -> new IllegalArgumentException("스케줄을 찾을 수 없습니다.")))
            .toList();

        if (savedSchedules.stream().anyMatch(savedSchedule -> savedSchedule.isConflictWith(schedule))) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }

        if (anniversaryRepository.isAnniversary(schedule.date())) {
            throw new IllegalArgumentException("기념일에는 예약할 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAllReservation() {
        return reservationRepository.findAll().stream()
            .map(reservation -> {
                final Schedule schedule = scheduleRepository.findById(reservation.getTicket()).orElseThrow(
                    () -> new IllegalArgumentException("스케줄을 찾을 수 없습니다."));

                return ReservationResponse.of(schedule, reservation);
            }).toList();
    }

    @Transactional(readOnly = true)
    public ReservationResponse findReservation(final Ticket ticket) {
        final Reservation reservation = reservationRepository.findByTicket(ticket).orElseThrow(
            () -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        final Schedule schedule = scheduleRepository.findById(reservation.getTicket()).orElseThrow(
            () -> new IllegalArgumentException("스케줄을 찾을 수 없습니다."));

        validateAvailableReservation(reservation);

        return ReservationResponse.of(schedule, reservation);
    }

    private void validateAvailableReservation(final Reservation reservation) {
        if (reservation.getStatus() == ReservationStatus.CANCELED) {
            throw new IllegalArgumentException("취소된 예약 입니다.");
        }
    }

    @Transactional
    public ReservationResponse cancelReservation(final Ticket ticket) {  // 리팩터링 내성을 위해 ReservationResponse을 반환
        final Reservation reservation = reservationRepository.findByTicket(ticket).orElseThrow(
            () -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        final Schedule schedule = scheduleRepository.findById(ticket).orElseThrow(
            () -> new IllegalArgumentException("스케줄을 찾을 수 없습니다."));

        reservation.cancel();

        return ReservationResponse.of(schedule, reservation);
    }
}
