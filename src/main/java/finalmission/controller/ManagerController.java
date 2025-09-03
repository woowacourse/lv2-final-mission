package finalmission.controller;

import finalmission.dto.ManagerCreateRequest;
import finalmission.dto.ManagerResponse;
import finalmission.service.ManagerService;
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
@RequestMapping("/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping
    public ResponseEntity<List<ManagerResponse>> getAllManagers() {
        List<ManagerResponse> allManagers = managerService.getAllManagers();
        return ResponseEntity.ok(allManagers);
    }

    @PostMapping("/agency")
    public ResponseEntity<ManagerResponse> createManager(@RequestBody ManagerCreateRequest request) {
        ManagerResponse saved = managerService.createManager(request);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/agency/{managerId}")
    public ResponseEntity<Void> removeManager(@PathVariable("managerId") Long managerId) {
        managerService.deleteManager(managerId);
        return ResponseEntity.noContent().build();
    }
}

