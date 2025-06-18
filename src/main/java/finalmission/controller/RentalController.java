package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.common.Role;
import finalmission.dto.request.RentalRequest;
import finalmission.dto.response.RentalResponse;
import finalmission.service.RentalService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @CheckRole(Role.ADMIN)
    @GetMapping
    public ResponseEntity<List<RentalResponse>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getRentals());
    }

    @PostMapping
    public ResponseEntity<RentalResponse> createRental(@RequestBody RentalRequest rentalRequest) {
        RentalResponse rental = rentalService.createRental(rentalRequest);
        return ResponseEntity.created(URI.create("/rentals/" + rental.id())).build();
    }
}
