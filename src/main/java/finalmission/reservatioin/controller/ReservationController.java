package finalmission.reservatioin.controller;

import finalmission.customer.entity.Customer;
import finalmission.customer.resolver.LoginCustomer;
import finalmission.reservatioin.controller.dto.response.CurrentStateReservationResponse;
import finalmission.reservatioin.controller.dto.request.ReservationCreateRequest;
import finalmission.reservatioin.controller.dto.response.ReservationResponse;
import finalmission.reservatioin.entity.Reservation;
import finalmission.reservatioin.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> save(
        @LoginCustomer Customer customer,
        @Valid @RequestBody ReservationCreateRequest request
    ) {
        ReservationResponse save = reservationService.save(customer.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @LoginCustomer Customer customer,
            @PathVariable("id") Long id
    ) {
        reservationService.deleteById(customer.getId(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(
            @LoginCustomer Customer customer
    ) {
        List<ReservationResponse> findAll = reservationService.findAllByMemberId(customer.getId());
        return ResponseEntity.ok().body(findAll);
    }

    @GetMapping
    public ResponseEntity<List<CurrentStateReservationResponse>> getCurrentStateOfReservation() {
        List<CurrentStateReservationResponse> all = reservationService.findAllReservationWithNumberOfPeople();
        return ResponseEntity.ok().body(all);
    }
}
