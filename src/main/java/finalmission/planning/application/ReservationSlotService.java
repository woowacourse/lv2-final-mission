package finalmission.planning.application;

import finalmission.planning.domain.PlanDate;
import finalmission.planning.domain.ReservationSlot;
import finalmission.planning.domain.TimeSlot;
import finalmission.planning.infra.repository.ReservationSlotRepository;
import finalmission.planning.ui.dto.request.CreateReservationSlotRequest;
import finalmission.planning.ui.dto.response.ReservationSlotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationSlotService {

    private final ReservationSlotRepository reservationSlotRepository;

    public ReservationSlotResponse registerReservationSlot(CreateReservationSlotRequest request) {
        PlanDate planDate = new PlanDate(request.date());
        TimeSlot timeSlot = new TimeSlot(request.startTime(), request.endTime());
        ReservationSlot reservationSlot = new ReservationSlot(planDate, timeSlot);
        ReservationSlot saved = reservationSlotRepository.save(reservationSlot);
        return ReservationSlotResponse.from(saved);
    };
}
