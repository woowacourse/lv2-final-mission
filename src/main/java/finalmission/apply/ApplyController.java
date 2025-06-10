package finalmission.apply;

import finalmission.apply.domain.Apply;
import finalmission.facade.application.CreateApplyUseCase;
import finalmission.facade.application.DeleteApplyUseCase;
import finalmission.facade.application.GetApplyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
public class ApplyController {

    private final GetApplyUseCase getApplyUseCase;
    private final CreateApplyUseCase createApplyUseCase;
    private final DeleteApplyUseCase deleteApplyUseCase;

    // TODO validation Password

    @GetMapping
    public ResponseEntity<List<Apply>> get(@RequestBody final ApplyRequest applyRequest) {
        final List<Apply> result = getApplyUseCase.execute(applyRequest.nickname());

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody final ApplyRequest applyRequest) {
        createApplyUseCase.execute(applyRequest.nickname());

        return ResponseEntity.status(HttpStatus.CREATED).body("신청 성공");
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody final ApplyRequest applyRequest) {
        deleteApplyUseCase.execute(applyRequest.nickname());

        return ResponseEntity.ok().body("삭제 완료");
    }
}
