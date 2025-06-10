package finalmission.music.controller;

import finalmission.music.controller.dto.MemberRequest;
import finalmission.music.controller.dto.MemberResponse;
import finalmission.music.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> create(@RequestBody final MemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.save(request));
    }
}
