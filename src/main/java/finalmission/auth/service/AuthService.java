package finalmission.auth.service;

import finalmission.auth.dto.LoginDto;
import finalmission.auth.exception.InvalidTokenException;
import finalmission.global.token.JwtTokenProvider;
import finalmission.member.exception.NotFoundUserException;
import finalmission.member.exception.UserBadRequestException;
import finalmission.user.User;
import finalmission.user.domain.dto.UserResponseDto;
import finalmission.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository memberRepository;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository memberRepository, JwtTokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    public String login(LoginDto loginDto) {
        final String email = loginDto.email();
        final String password = loginDto.password();
        User savedUser = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(UserBadRequestException::new);
        return tokenProvider.createToken(new TokenInfoDto(savedUser.getId(), savedUser.getRole()));
    }

    public UserResponseDto findMemberByToken(String token) {
        if (!tokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
        String payload = tokenProvider.getPayload(token);
        User user = findMember(payload);
        return UserResponseDto.from(user);
    }

    public User findMember(String payload) {
        Long id = Long.valueOf(payload);
        return memberRepository.findById(id)
                .orElseThrow(NotFoundUserException::new);
    }
}
