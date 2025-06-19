package finalmission.presentation;

import finalmission.domain.entity.Member;
import finalmission.domain.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/trainers")
    public ResponseEntity<Void> selectTrainer(
            @AuthInfo Member member,
            @RequestParam("trainerId") Long trainerId
    ) {
        memberService.selectTrainer(member, trainerId);
        return ResponseEntity.noContent().build();
    }
}
