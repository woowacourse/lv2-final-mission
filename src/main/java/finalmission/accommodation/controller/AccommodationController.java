package finalmission.accommodation.controller;

import finalmission.accommodation.dto.AccommodationResponse;
import finalmission.accommodation.dto.CreateAccommodationRequest;
import finalmission.accommodation.service.AccommodationService;
import finalmission.global.auth.LoginAdmin;
import finalmission.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @PostMapping
    public ResponseEntity<AccommodationResponse> create(@LoginAdmin Member admin,
                                                        @RequestBody CreateAccommodationRequest request) {
        AccommodationResponse response = accommodationService.create(admin, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@LoginAdmin Member admin,
                                       @PathVariable(value = "id") long accommodationId) {
        accommodationService.delete(admin, accommodationId);
        return ResponseEntity.noContent().build();
    }
}
