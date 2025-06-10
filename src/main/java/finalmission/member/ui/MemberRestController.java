package finalmission.member.ui;

import static finalmission.auth.domain.AuthRole.ADMIN;
import static finalmission.auth.domain.AuthRole.MEMBER;

import finalmission.auth.domain.RequiresRole;
import finalmission.member.application.MemberService;
import finalmission.member.domain.Member;
import finalmission.member.ui.dto.MemberResponse;
import finalmission.member.ui.dto.SignUpRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> create(
            @RequestBody @Valid final SignUpRequest request
    ) {
        final MemberResponse response = memberService.create(request);

        return ResponseEntity.created(URI.create("/members/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    @RequiresRole(authRoles = {ADMIN, MEMBER})
    public ResponseEntity<Void> delete(
            @PathVariable final Long id,
            final Member member
    ) {
        memberService.delete(member, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    @RequiresRole(authRoles = {ADMIN})
    public ResponseEntity<List<MemberResponse>> findAll() {
        final List<MemberResponse> responses = memberService.findAllNames();

        return ResponseEntity.status(HttpStatus.OK)
                .body(responses);
    }
}
