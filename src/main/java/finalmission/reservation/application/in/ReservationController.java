package finalmission.reservation.application.in;

import finalmission.reservation.application.ReservationService;
import finalmission.reservation.application.in.dto.Reserve;
import finalmission.reservation.application.out.MyReservation;
import finalmission.reservation.application.out.dto.MyReservationWaitingCount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예약 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "예약하기 API")
    @PostMapping
    public ResponseEntity<Void> reserve(@RequestBody final Reserve request) {
        reservationService.reserve(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "대기 순번 조회 API")
    @GetMapping("/{reservationId}/waiting-count/{memberId}")
    public ResponseEntity<MyReservationWaitingCount> getMyWaitingCount(
            @PathVariable final Long reservationId,
            @PathVariable final Long memberId
    ) {
        MyReservationWaitingCount myReservationWaitingCount = reservationService.getMyWaitingCount(
                reservationId, memberId);

        return ResponseEntity.ok(myReservationWaitingCount);
    }

    @Operation(summary = "대기 취소 API")
    @PostMapping("/{reservationId}/leave/{memberId}")
    public ResponseEntity<Void> leave(
            @PathVariable final Long reservationId,
            @PathVariable final Long memberId
    ) {
        reservationService.leave(reservationId, memberId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "내 예약 상세 조회 API")
    @GetMapping("/{reservationId}/my-reservation/{memberId}")
    public ResponseEntity<MyReservation> getMyReservation(
            @PathVariable final Long reservationId,
            @PathVariable final Long memberId
    ) {
        MyReservation myReservation = reservationService.getMyReservation(reservationId, memberId);

        return ResponseEntity.ok(myReservation);
    }
}
