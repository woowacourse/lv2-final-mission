package finalmission.mungPlan.application;

import finalmission.mungPlan.domain.PlanDate;
import finalmission.mungPlan.domain.TimeSlot;
import finalmission.mungPlan.domain.Reservation;
import finalmission.mungPlan.domain.TimeSlots;
import finalmission.mungPlan.domain.User;
import finalmission.mungPlan.exception.NotFoundException;
import finalmission.mungPlan.infra.PlanDateRepository;
import finalmission.mungPlan.infra.ReservationRepository;
import finalmission.mungPlan.infra.UserRepository;
import finalmission.mungPlan.ui.dto.CreateReservationRequest;
import finalmission.mungPlan.ui.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PlanDateRepository planDateRepository;
    private final UserRepository userRepository;

    public ReservationResponse createReservation(CreateReservationRequest request) {
        Long planDateId = request.planDateId();

        PlanDate planDate = planDateRepository.findById(planDateId)
                .orElseThrow(() -> new NotFoundException("planDateId", planDateId));

        TimeSlots timeSlots = planDate.getTimeSlots();
        TimeSlot timeSlot = timeSlots.findByStartAt(request.planTimeStartAt())
                .orElseThrow(() -> new NotFoundException("planTime", request.planTimeStartAt()));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("user", request.userId()));

        Reservation saved = reservationRepository.save(Reservation.createNew(planDate, timeSlot, user));
        return new ReservationResponse(saved);
    }
}
