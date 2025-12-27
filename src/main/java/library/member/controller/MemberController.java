package library.member.controller;

import library.member.dto.MemberRequest;
import library.member.dto.MemberResponse;
import library.member.domain.Member;
import library.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest memberRequest) {
        Member member = memberService.createMember(memberRequest.email());
        return ResponseEntity.status(201).body(MemberResponse.from(member));
    }
} 