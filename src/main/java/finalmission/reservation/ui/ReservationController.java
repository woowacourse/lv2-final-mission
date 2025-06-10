package finalmission.reservation.ui;

import finalmission.auth.annotation.LoginMemberId;
import finalmission.reservation.application.ReservationService;
import finalmission.reservation.application.dto.ReservationRequest;
import finalmission.reservation.application.dto.ReservationResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody ReservationRequest request,
            @Parameter(hidden = true) @LoginMemberId Long memberId) {
        ReservationResponse response = reservationService.create(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(
            @Parameter(hidden = true) @LoginMemberId Long memberId) {
        List<ReservationResponse> response = reservationService.getMyReservation(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAll(
            @Parameter(description = "회의실 ID") @RequestParam(required = false) Long roomId
    ) {
        List<ReservationResponse> response = reservationService.getRoomReservation(roomId);
        return ResponseEntity.ok(response);
    }
}
