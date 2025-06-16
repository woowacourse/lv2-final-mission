package finalmission.customer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import finalmission.customer.controller.dto.request.TokenLoginCreateRequest;
import finalmission.customer.infrastructure.JwtTokenProvider;
import finalmission.customer.repository.CustomerJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private CustomerJpaRepository customerJpaRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private String email = "neo@com";
    private String password = "1234";


    @Test
    @DisplayName("등록되지 않은 회원이면 로그인에 실패한다.")
    void tokenLoginFailTest() {
        //given
        TokenLoginCreateRequest tokenLoginCreateRequest = new TokenLoginCreateRequest(email, password);
        when(customerJpaRepository.existsByEmailAndPassword(any(), any())).thenReturn(false);

        //when-then
        assertThatThrownBy(() -> authService.tokenLogin(tokenLoginCreateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 회원입니다. 아이디와 비밀번호를 확인해 주세요.");
    }

    @Test
    @DisplayName("등록된 회원이라면 로그인에 성공한다.")
    void tokenLoginTest() {
        //given
        TokenLoginCreateRequest tokenLoginCreateRequest = new TokenLoginCreateRequest(email, password);
        when(customerJpaRepository.existsByEmailAndPassword(any(), any())).thenReturn(true);

        //when-then
        assertDoesNotThrow(() -> authService.tokenLogin(tokenLoginCreateRequest));
    }
}
