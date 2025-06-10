package finalmission.reservation.presentation;

import static finalmission.reservation.presentation.ReservationController.RESERVATION_BASE_URL;

import finalmission.common.Login;
import finalmission.member.dto.LoginMember;
import finalmission.reservation.dto.MyReservationRequest;
import finalmission.reservation.dto.MyReservationResponse;
import finalmission.reservation.service.ReservationService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RESERVATION_BASE_URL)
public class ReservationController {

    public static final String RESERVATION_BASE_URL = "/reservations";
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<MyReservationResponse>> getReservation(
            @ModelAttribute MyReservationRequest request) {
        List<MyReservationResponse> response = reservationService.getMyReservations();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MyReservationResponse> save(
            @RequestBody final MyReservationRequest request,
            @Login final LoginMember loginMember
    ) {
        MyReservationResponse response = reservationService.save(request, loginMember);
        URI locationUri = URI.create(RESERVATION_BASE_URL + "/" + response.id());
        return ResponseEntity.created(locationUri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MyReservationResponse> delete(@PathVariable final Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
