package finalmission.service;

import finalmission.dto.request.LoginRequest;
import finalmission.entity.Member;
import finalmission.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findByEmailAndPassword(LoginRequest loginRequest) {
        return memberRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 로그인 정보입니다."));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 멤버가 없습니다"));
    }
}
