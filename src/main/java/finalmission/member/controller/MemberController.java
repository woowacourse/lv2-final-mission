package finalmission.member.controller;

import finalmission.global.auth.annotation.AuthenticationPrincipal;
import finalmission.global.auth.annotation.RoleRequired;
import finalmission.global.auth.dto.LoginMember;
import finalmission.member.dto.request.MemberRequest;
import finalmission.member.dto.response.MemberResponse;
import finalmission.member.entity.RoleType;
import finalmission.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
@Tag(name = "멤버", description = "멤버 관련 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "멤버 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", useReturnTypeSchema = true),
    })
    @SecurityRequirements(value = {})
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(
            @RequestBody @Valid MemberRequest request
    ) {
        MemberResponse response = memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "어드민 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", useReturnTypeSchema = true),
    })
    @SecurityRequirements(value = {})
    @PostMapping("/admin")
    public ResponseEntity<MemberResponse> createMemberAsAdmin(
            @RequestBody @Valid MemberRequest request
    ) {
        MemberResponse response = memberService.createMemberAsAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "모든 멤버 조회 (어드민 권한)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = RoleType.ADMIN)
    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAllMembers() {
        List<MemberResponse> responses = memberService.findAllMembers();
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "특정 멤버 조회 (어드민 권한)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = RoleType.ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findMemberById(
            @PathVariable("id") Long id
    ) {
        MemberResponse response = memberService.findMemberById(id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "내 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = {RoleType.USER, RoleType.ADMIN})
    @GetMapping("/mine")
    public ResponseEntity<MemberResponse> findMine(
            @Parameter(hidden = true) @AuthenticationPrincipal LoginMember loginMember
    ) {
        MemberResponse response = memberService.findMemberById(loginMember.id());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "어드민으로 승격 (어드민 권한)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = RoleType.ADMIN)
    @PatchMapping("/admin")
    public ResponseEntity<Void> promoteToAdmin(
            Long id
    ) {
        memberService.promoteToAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 탈퇴 처리 (어드민 권한)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", useReturnTypeSchema = true),
    })
    @RoleRequired(roleType = RoleType.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(
            @PathVariable("id") Long id
    ) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", useReturnTypeSchema = true),
    })
    @DeleteMapping("/mine")
    @RoleRequired(roleType = {RoleType.USER, RoleType.ADMIN})
    public ResponseEntity<Void> deleteMine(
            @Parameter(hidden = true) @AuthenticationPrincipal LoginMember loginMember
    ) {
        memberService.deleteMember(loginMember.id());
        return ResponseEntity.noContent().build();
    }
}
