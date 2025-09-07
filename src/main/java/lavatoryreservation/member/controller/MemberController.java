package lavatoryreservation.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lavatoryreservation.auth.JwtCookie;
import lavatoryreservation.external.auth.JwtTokenProvider;
import lavatoryreservation.member.domain.Member;
import lavatoryreservation.member.dto.LoginDto;
import lavatoryreservation.member.dto.SignupDto;
import lavatoryreservation.member.service.MemberService;

@RestController
@RequestMapping("/api/member/")
@Tag(name = "회원 관리", description = "회원 가입, 로그인 관련 API")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
    })
    @PostMapping
    public ResponseEntity<Long> addMember(
            @Parameter(description = "회원 가입 정보", required = true)
            @RequestBody SignupDto signupDto) {
        
        logger.info("회원 가입 요청 - 이메일: {}", signupDto.email());
        
        try {
            Long memberId = memberService.addMember(signupDto);
            logger.info("회원 가입 성공 - 회원ID: {}", memberId);
            return ResponseEntity.ok(memberId);
        } catch (Exception e) {
            logger.error("회원 가입 실패 - 이메일: {}, 에러: {}", signupDto.email(), e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "로그인", description = "이메일을 통해 로그인하고 JWT 토큰을 쿠키로 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공 (JWT 토큰이 쿠키에 설정됨)"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원")
    })
    @GetMapping
    public ResponseEntity<Void> login(
            @Parameter(description = "로그인 정보", required = true)
            LoginDto loginDto, 
            HttpServletResponse response) {
        
        logger.info("로그인 요청 - 이메일: {}", loginDto.email());
        
        try {
            Member member = memberService.getByEmail(loginDto);
            String jwt = jwtTokenProvider.createToken(member);
            JwtCookie jwtCookie = new JwtCookie(jwt);
            
            logger.info("로그인 성공 - 회원ID: {}", member.getId());
            
            return ResponseEntity.ok()
                    .header(JwtTokenProvider.getCookieKey(), jwtCookie.cookie())
                    .build();
        } catch (Exception e) {
            logger.error("로그인 실패 - 이메일: {}, 에러: {}", loginDto.email(), e.getMessage());
            throw e;
        }
    }
}
