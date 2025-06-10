package finalmission.presentation;

import finalmission.application.RentService;
import finalmission.domain.Member;
import finalmission.dto.RequestRent;
import finalmission.dto.ResponseRent;
import finalmission.dto.ResponseRentDetail;
import finalmission.infrastructure.resolver.LoginMember;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rent")
@RestController
@RequiredArgsConstructor
public class RentController {

    private static final String RENT_URI_FORMAT = "/rent/%d";

    private final RentService rentService;

    @PostMapping
    public ResponseEntity<Void> rent(@LoginMember Member loginMember, @RequestBody RequestRent requestRent) {
        Long id = rentService.rent(loginMember, requestRent);
        return ResponseEntity.created(URI.create(RENT_URI_FORMAT.formatted(id))).build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseRent>> getAllRent() {
        return ResponseEntity.ok(rentService.getAll());
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ResponseRentDetail>> getMine(@LoginMember Member loginMember) {
        return ResponseEntity.ok(rentService.getAllByMember(loginMember));
    }

    @DeleteMapping("{rentId}")
    public ResponseEntity<Void> cancelRent(
            @LoginMember Member loginMember,
            @PathVariable(value = "rentId") Long rentId
    ) {
        rentService.cancelById(loginMember, rentId);
        return ResponseEntity.noContent().build();
    }
}
