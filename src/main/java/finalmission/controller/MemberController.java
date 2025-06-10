package finalmission.controller;

import finalmission.dto.request.RegisterRequest;
import finalmission.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/{roomId}")
    public void register(
            @PathVariable("roomId") String roomId,
            @RequestBody RegisterRequest request
    ) {
        memberService.register(roomId, request.name(), request.password());
    }
}
