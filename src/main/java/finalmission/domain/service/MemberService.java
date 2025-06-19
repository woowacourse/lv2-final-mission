package finalmission.domain.service;

import finalmission.domain.entity.Member;
import finalmission.domain.entity.Trainer;
import finalmission.domain.repository.MemberRepository;
import finalmission.domain.repository.TrainerRepository;
import finalmission.domain.service.dto.LoginRequest;
import finalmission.domain.service.dto.SignUpRequest;
import finalmission.infrastructure.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepository memberRepository, TrainerRepository trainerRepository,
                         JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void registerMember(SignUpRequest request) {
        Member member = Member.createWithoutId(request.name(), request.email(), request.password(), 2);

        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이메일은 중복될 수 없습니다.");
        }
        memberRepository.save(member);
    }

    public String publishLoginToken(LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이메일이나 비밀번호가 올바르지 않습니다."));
        if (!member.matchPassword(password)) {
            throw new IllegalArgumentException("[ERROR] 이메일이나 비밀번호가 올바르지 않습니다.");
        }
        return jwtTokenProvider.createToken(member);
    }

    public void selectTrainer(Member loginMember, Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 선생님입니다: " + trainerId));
        if (loginMember.getTrainer() != null) {
            throw new IllegalArgumentException("[ERROR] 담당 선생님이 존재하는 경우 관리자의 승인이 필요합니다.");
        }
        loginMember.selectTrainer(trainer);
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 사용자입니다: " + memberId));
    }
}
