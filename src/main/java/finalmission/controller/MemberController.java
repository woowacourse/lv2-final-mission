package finalmission.controller;

import finalmission.client.RandomNameClient;
import finalmission.domain.Member;
import finalmission.dto.layer.MemberCreationContent;
import finalmission.dto.request.SignupRequest;
import finalmission.servcie.MemberService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final RandomNameClient randomNameClient;

    public MemberController(MemberService memberService, RandomNameClient randomNameClient) {
        this.memberService = memberService;
        this.randomNameClient = randomNameClient;
    }

    @PostMapping("/member")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
        MemberCreationContent creationContent = makeMemberCreationContent(request);
        Member member = memberService.addMember(creationContent);
        return ResponseEntity.created(URI.create("member/" + member.getId())).build();
    }

    private MemberCreationContent makeMemberCreationContent(SignupRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            String name = randomNameClient.generateRandomNames(1).getFirst();
            return new MemberCreationContent(request.email(), request.password(), name);
        }
        return new MemberCreationContent(request);
    }
}
