package finalmission.api.v1.reservation.controller;

import finalmission.api.v1.reservation.dto.ReservationDeleteRequest;
import finalmission.api.v1.reservation.dto.ReservationDetailGetRequest;
import finalmission.api.v1.reservation.dto.ReservationDetailResponse;
import finalmission.api.v1.reservation.dto.ReservationForAllUserResponse;
import finalmission.api.v1.reservation.dto.ReservationModifyRequest;
import finalmission.api.v1.reservation.dto.ReservationRequest;
import finalmission.api.v1.reservation.dto.ReservationResponse;
import finalmission.api.v1.reservation.service.ReservationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationForAllUserResponse> getAllReservation() {
        return reservationService.getAllReservation();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse resisterReservation(@Valid @RequestBody final ReservationRequest request) {
        return reservationService.resisterReservation(request);
    }

    @GetMapping("/{id}")
    public ReservationDetailResponse getReservationDetail(
            @NotNull @PathVariable final Long id,
            @Valid @RequestBody final ReservationDetailGetRequest request
    ) {
        return reservationService.getReservationDetail(id, request);
    }

    @PatchMapping("/{id}")
    public ReservationResponse modifyReservation(
            @NotNull @PathVariable final Long id,
            @Valid @RequestBody final ReservationModifyRequest request
    ) {
        return reservationService.modifyReservation(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(
            @NotNull @PathVariable final Long id,
            @Valid @RequestBody final ReservationDeleteRequest request
    ) {
        reservationService.deleteReservation(id, request);
    }
}
