package finalmission.music.service;

import finalmission.music.controller.dto.LoginRequest;
import finalmission.music.domain.Member;
import finalmission.music.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(final LoginRequest request) {
        return memberRepository.findById(request.name())
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 로그인 할 수 없습니다."));
    }
}
