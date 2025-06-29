package finalmission.waiting.ui;


import static finalmission.auth.domain.AuthRole.ADMIN;

import finalmission.auth.domain.RequiresRole;
import finalmission.waiting.application.AdminWaitingService;
import finalmission.waiting.ui.dto.CreateWaitingRequest;
import finalmission.waiting.ui.dto.WaitingResponse;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/admin/waitings")
@RequiresRole(authRoles = {ADMIN})
@RequiredArgsConstructor
public class AdminWaitingRestController {

    private final AdminWaitingService adminWaitingService;

    @PostMapping
    public ResponseEntity<WaitingResponse> create(
            @RequestBody @Valid final CreateWaitingRequest request
    ) {
        final WaitingResponse response = adminWaitingService.create(request);

        return ResponseEntity.created(URI.create("/admin/waitings/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deny(
            @PathVariable final Long id
    ) {
        adminWaitingService.deleteAsAdmin(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WaitingResponse>> findAll() {
        final List<WaitingResponse> responses = adminWaitingService.findAll();

        return ResponseEntity.ok(responses);
    }
}
