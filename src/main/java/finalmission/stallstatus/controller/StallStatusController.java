package finalmission.stallstatus.controller;

import finalmission.auth.resolver.Authenticated;
import finalmission.auth.resolver.MemberPrincipal;
import finalmission.stallstatus.controller.dto.request.StallStatusCreateRequest;
import finalmission.stallstatus.controller.dto.response.StallStatusCreateResponse;
import finalmission.stallstatus.controller.dto.response.StallStatusFindResponse;
import finalmission.stallstatus.service.StallStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/status")
public class StallStatusController {

    private final StallStatusService stallStatusService;

    public StallStatusController(StallStatusService stallStatusService) {
        this.stallStatusService = stallStatusService;
    }

    @PostMapping
    public ResponseEntity<StallStatusCreateResponse> postStallStatus(@RequestBody StallStatusCreateRequest request, @Authenticated MemberPrincipal principal) {
        StallStatusCreateResponse response = stallStatusService.create(request, principal.memberId());
        return ResponseEntity.created(URI.create("/status/" + response.stallStatusId())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StallStatusFindResponse> getStallStatus(@PathVariable Long id) {
        StallStatusFindResponse response = stallStatusService.getStallStatus(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mine")
    public ResponseEntity<MyStallStatusResponse> getStallStatus(@Authenticated MemberPrincipal principal) {
        MyStallStatusResponse response = stallStatusService.getMyStallStatus(principal.memberId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStallStatus(@PathVariable Long id, @Authenticated MemberPrincipal principal) {
        stallStatusService.deleteStallStatus(id, principal.memberId());
        return ResponseEntity.noContent().build();
    }
}
