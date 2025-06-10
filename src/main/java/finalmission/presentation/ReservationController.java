package finalmission.presentation;

import finalmission.domain.service.ReservationService;
import finalmission.domain.service.dto.TrainerReservationResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 담당 선생님 별 예약 현황을 확인한다
    @GetMapping("/trainers/{trainerId}")
    public List<TrainerReservationResponse> getTrainersReservations(@PathVariable("trainerId") Long trainerId) {
        return reservationService.getTrainersReservations(trainerId);
    }

    // 수업을 예약한다
}
