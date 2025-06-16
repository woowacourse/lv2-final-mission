package finalmission.service;

import finalmission.domain.member.Coach;
import finalmission.domain.member.Crew;
import finalmission.domain.member.Member;
import finalmission.domain.login.JwtProvider;
import finalmission.dto.LoginRequestDto;
import finalmission.domain.login.Token;
import finalmission.repository.CoachRepository;
import finalmission.repository.CrewRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final JwtProvider jwtProvider;
    private final CrewRepository crewRepository;
    private final CoachRepository coachRepository;

    public LoginService(
        JwtProvider jwtProvider,
        CrewRepository crewRepository,
        CoachRepository coachRepository
    ) {
        this.jwtProvider = jwtProvider;
        this.crewRepository = crewRepository;
        this.coachRepository = coachRepository;
    }

    public Token coachLogin(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.email();
        Member coach = findCoachByEmail(email);
        return jwtProvider.createToken(coach);
    }

    public Token crewLogin(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.email();
        Member crew = findCrewByEmail(email);
        return jwtProvider.createToken(crew);
    }

    public Coach findByCoachId(Long id) {
        return coachRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코치 ID 입니다."));
    }

    public Crew findByCrewId(Long id) {
        return crewRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루 ID 입니다."));
    }

    private Member findCoachByEmail(String email) {
        return coachRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메일입니다."));
    }

    private Member findCrewByEmail(String email) {
        return crewRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메일입니다."));
    }
}
