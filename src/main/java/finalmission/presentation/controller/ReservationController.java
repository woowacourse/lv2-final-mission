package finalmission.presentation.controller;

import finalmission.dto.request.ReservationRequest;
import finalmission.dto.response.ReservationResponse;
import finalmission.presentation.auth.Authenticated;
import finalmission.service.ReservationService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@Authenticated Long memberId,
                                                       @RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(memberId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<ReservationResponse> getReservations(
            @RequestParam("toiletId") Long toiletId,
            @RequestParam("date") LocalDate date
    ) {
        return reservationService.findReservationByToiletIdAndDate(toiletId, date);
    }

    @GetMapping("/me")
    public List<ReservationResponse> getMyReservations(@Authenticated Long memberId) {
        return reservationService.findReservationByMemberId(memberId);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@Authenticated Long memberId,
                                                  @PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservationById(memberId, reservationId);
        return ResponseEntity.noContent().build();
    }
}
