package finalmission.controller;

import finalmission.annotation.AccessToken;
import finalmission.domain.Reservation;
import finalmission.dto.layer.AccessTokenContent;
import finalmission.dto.layer.ReservationCreationContent;
import finalmission.dto.layer.ReservationUpdateContent;
import finalmission.dto.request.AddReservationRequest;
import finalmission.dto.request.UpdateReservationByIdRequest;
import finalmission.dto.response.FindAllReservationByMember;
import finalmission.dto.response.FindAllReservationBySeat;
import finalmission.dto.response.FindReservationById;
import finalmission.servcie.ReservationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/reservation", params = {"seatId"})
    public List<FindAllReservationBySeat> findAllReservationBySeat(
            @RequestParam("seatId") Long seatId
    ) {
        return reservationService.findAllReservationBySeats(seatId);
    }

    @GetMapping(value = "/member/reservation")
    public List<FindAllReservationByMember> findAllReservationByMember(
            @AccessToken AccessTokenContent accessToke
    ) {
        return reservationService.findAllReservationByMember(accessToke.memberId());
    }

    @GetMapping(value = "/reservation/{reservationId}")
    public FindReservationById findReservationById(
            @AccessToken AccessTokenContent accessToke,
            @PathVariable("reservationId") Long reservationId
    ) {
        return reservationService.findById(accessToke.memberId(), reservationId);
    }

    @PostMapping("/reservation")
    public ResponseEntity<Void> addReservation(
            @AccessToken AccessTokenContent accessToken,
            @Valid @RequestBody AddReservationRequest request
    ) {
        ReservationCreationContent creationContent = new ReservationCreationContent(accessToken.memberId(), request);
        Reservation reservation = reservationService.addReservation(creationContent);
        return ResponseEntity.created(URI.create("reservation/" + reservation.getId())).build();
    }

    @PutMapping(value = "/reservation")
    public ResponseEntity<Void> updateReservationById(
            @AccessToken AccessTokenContent accessToke,
            @Valid @RequestBody UpdateReservationByIdRequest request
    ) {
        ReservationUpdateContent updateContent = new ReservationUpdateContent(request);
        reservationService.updateReservationById(accessToke.memberId(), updateContent);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservationById(
            @AccessToken AccessTokenContent accessToke,
            @PathVariable("reservationId") Long reservationId
    ) {
        reservationService.deleteReservation(accessToke.memberId(), reservationId);
        return ResponseEntity.noContent().build();
    }
}
