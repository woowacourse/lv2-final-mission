package finalmission.presentation;

import finalmission.domain.entity.Member;
import finalmission.domain.entity.ReservationStatus;
import finalmission.domain.service.ReservationService;
import finalmission.domain.service.dto.ReservationDetailResponse;
import finalmission.domain.service.dto.ReservationLessonRequest;
import finalmission.domain.service.dto.TrainerReservationResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void reserveLesson(
            @AuthInfo Member member,
            @RequestBody ReservationLessonRequest request
    ) {
        reservationService.reserveLesson(member, request);
    }

    @GetMapping("/trainers/{trainerId}")
    public List<TrainerReservationResponse> getTrainersReservations(@PathVariable("trainerId") Long trainerId) {
        return reservationService.getTrainersReservations(trainerId);
    }

    @GetMapping("/reserved/mine")
    public List<ReservationDetailResponse> getMyReservations(@AuthInfo Member member) {
        return reservationService.getReservationsMemberState(member, ReservationStatus.RESERVED);
    }

    @GetMapping("/waiting/mine")
    public List<ReservationDetailResponse> getMyWaitings(@AuthInfo Member member) {
        return reservationService.getReservationsMemberState(member, ReservationStatus.WAITING);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(
            @AuthInfo Member member,
            @PathVariable("reservationId") Long reservationId
    ) {
        reservationService.cancelReservation(member, reservationId);
        return ResponseEntity.noContent().build();
    }
}
