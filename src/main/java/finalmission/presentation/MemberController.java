package finalmission.presentation;

import finalmission.application.MemberService;
import finalmission.application.dto.request.CreateMemberRequest;
import finalmission.application.dto.response.CreateMemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<CreateMemberResponse> joinMember(
            @Valid @RequestBody CreateMemberRequest createMemberRequest
    ) {
        CreateMemberResponse createMemberResponse = memberService.create(createMemberRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createMemberResponse);
    }
}
