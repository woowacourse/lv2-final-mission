package finalmission.presentation.controller;

import finalmission.dto.MemberRegisterDto;
import finalmission.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public void signUp(@RequestBody MemberRegisterDto memberRegisterDto) {
        memberService.signUp(memberRegisterDto);
    }

    @GetMapping("/random-names")
    public List<String> getRandomNames(@RequestParam(name = "quantity") int quantity) {
        return memberService.getRandomNames(quantity);
    }
}
