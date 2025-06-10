package finalmission.presentation.controller;

import finalmission.dto.MemberRegisterDto;
import finalmission.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 관련 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping
    public void signUp(@RequestBody MemberRegisterDto memberRegisterDto) {
        memberService.signUp(memberRegisterDto);
    }

    @Operation(summary = "회원가입 시 제공하는 랜덤 이름 (기본 제공값 10개)")
    @GetMapping("/random-names")
    public List<String> getRandomNames(@RequestParam(name = "quantity") int quantity) {
        return memberService.getRandomNames(quantity);
    }
}
