package finalmission.mungPlan.application;

import finalmission.mungPlan.domain.PlanDate;
import finalmission.mungPlan.domain.Reservation;
import finalmission.mungPlan.domain.TimeSlot;
import finalmission.mungPlan.domain.TimeSlots;
import finalmission.mungPlan.domain.User;
import finalmission.mungPlan.exception.ForbiddenException;
import finalmission.mungPlan.exception.NotFoundException;
import finalmission.mungPlan.infra.PlanDateRepository;
import finalmission.mungPlan.infra.ReservationRepository;
import finalmission.mungPlan.infra.UserRepository;
import finalmission.mungPlan.ui.dto.CreateReservationRequest;
import finalmission.mungPlan.ui.dto.ReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PlanDateRepository planDateRepository;
    private final UserRepository userRepository;

    public ReservationResponse createReservation(CreateReservationRequest request) {

        PlanDate planDate = planDateRepository.findById(request.planDateId())
                .orElseThrow(() -> new NotFoundException("planDateId", request.planDateId()));

        TimeSlots timeSlots = planDate.getTimeSlots();
        TimeSlot timeSlot = timeSlots.findByStartAt(request.planTimeStartAt())
                .orElseThrow(() -> new NotFoundException("planTime", request.planTimeStartAt()));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("user", request.userId()));

        Reservation saved = reservationRepository.save(Reservation.createNew(planDate, timeSlot, user));
        return new ReservationResponse(saved);
    }

    public ReservationResponse getById(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);

        return new ReservationResponse(reservation);
    }

    public void cancelReservation(Long reservationId, String userId) {
        Reservation reservation = getReservationById(reservationId);
        if(reservation.isOwnedBy(userId)) {
            reservationRepository.delete(reservation);
            return;
        }
        throw new ForbiddenException("본인의 예약만 취소할 수 있습니다.");
    }

    private Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("reservation", reservationId));
    }

    public List<ReservationResponse> getAllByUserId(String userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }
}
