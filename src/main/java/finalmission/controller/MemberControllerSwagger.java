package finalmission.controller;

import finalmission.controller.dto.MemberLoginRequest;
import finalmission.controller.dto.MemberResponse;
import finalmission.controller.dto.MemberSignupRequest;
import finalmission.domain.Member;
import finalmission.global.config.AuthenticationPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "회원", description = "회원 관리 API")
public interface MemberControllerSwagger {

    @Operation(summary = "로그인 체크", description = "로그인 여부 확인후 회원 정보 반환 API")
    MemberResponse check(@AuthenticationPrincipal Member member);

    @Operation(summary = "회원가입", description = "회원 가입후 회원 정보 반환 API")
    MemberResponse signup(@RequestBody MemberSignupRequest request);

    @Operation(summary = "로그인", description = "로그인 후 토큰 발급 API")
    void login(@RequestBody MemberLoginRequest loginRequest, HttpServletResponse response);
}
