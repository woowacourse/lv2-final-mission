package finalmission.member.controller;

import finalmission.member.auth.annotation.RoleRequired;
import finalmission.member.controller.dto.MemberResponse;
import finalmission.member.domain.Role;
import finalmission.member.service.MemberFrontService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberFrontService memberFrontService;

    @RoleRequired(value = Role.ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable final Long id) {
        return ResponseEntity.ok(memberFrontService.get(id));
    }

    @RoleRequired(value = Role.ADMIN)
    @GetMapping
    public List<MemberResponse> getAllMember() {
        return memberFrontService.getAll();
    }
}
