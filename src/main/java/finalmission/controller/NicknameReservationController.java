package finalmission.controller;

import finalmission.controller.dto.MemberInfo;
import finalmission.controller.dto.ReservationCreateRequest;
import finalmission.controller.dto.ReservationCreateResponse;
import finalmission.controller.dto.ReservationResponse;
import finalmission.controller.dto.ReservationUpdateRequest;
import finalmission.controller.dto.ReservationUpdateResponse;
import finalmission.domain.NicknameReservation;
import finalmission.service.NicknameReservationService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/reservations")
@RestController
public class NicknameReservationController {

    private final NicknameReservationService nicknameReservationService;

    public NicknameReservationController(NicknameReservationService nicknameReservationService) {
        this.nicknameReservationService = nicknameReservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationCreateResponse> reserve(
            @RequestBody ReservationCreateRequest request,
            MemberInfo memberInfo
    ) {
        NicknameReservation reservation = nicknameReservationService.reserve(request.name(), memberInfo.memberId());
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
                .body(new ReservationCreateResponse(reservation));
    }

    /**
     * TODO
     * put? patch?
     */
    @PatchMapping("/{reservationId}")
    public ResponseEntity<ReservationUpdateResponse> update(
            @PathVariable(value = "reservationId") long reservationId,
            @RequestBody ReservationUpdateRequest request,
            MemberInfo memberInfo
    ) {
        nicknameReservationService.update(request.name(), reservationId, memberInfo.memberId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{reservationId}/confirm")
    public ResponseEntity<ReservationCreateResponse> confirm(
            @PathVariable(value = "reservationId") long reservationId,
            MemberInfo memberInfo
    ) {
        nicknameReservationService.confirm(reservationId, memberInfo.memberId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancel(
            @PathVariable(value = "reservationId") long reservationId,
            MemberInfo memberInfo
    ) {
        nicknameReservationService.cancel(reservationId, memberInfo.memberId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<NicknameReservation> reservations = nicknameReservationService.findAll();
        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::new)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ReservationResponse>> findMine(MemberInfo memberInfo) {
        List<NicknameReservation> reservations = nicknameReservationService.findMine(memberInfo.memberId());
        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::new)
                .toList();
        return ResponseEntity.ok().body(response);
    }
}
