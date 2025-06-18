package finalmission.venue.controller;

import finalmission.member.auth.annotation.RoleRequired;
import finalmission.member.domain.Role;
import finalmission.venue.controller.dto.VenueRequest;
import finalmission.venue.controller.dto.VenueResponse;
import finalmission.venue.service.VenueFrontService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueFrontService venueFrontService;

    @RoleRequired(value = Role.ADMIN)
    @PostMapping
    public ResponseEntity<VenueResponse> createVenue(@RequestBody final VenueRequest request) {
        return ResponseEntity.ok(venueFrontService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getVenue(@PathVariable final Long id) {
        return ResponseEntity.ok(venueFrontService.get(id));
    }

    @GetMapping
    public List<VenueResponse> getAllVenue() {
        return venueFrontService.getAll();
    }
}
