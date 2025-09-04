package finalmission.service;

import static finalmission.domain.TokenAuthRole.USER;

import finalmission.domain.Crew;
import finalmission.domain.TokenProcessor;
import finalmission.dto.request.CrewLoginRequest;
import finalmission.dto.request.CrewSignUpRequest;
import finalmission.dto.response.CrewLoginResponse;
import finalmission.repository.CrewRepository;
import finalmission.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewService {

    private final CrewRepository crewRepository;
    private final TokenProcessor tokenProcessor;
    private final MeetingRepository meetingRepository;

    public CrewLoginResponse login(CrewLoginRequest request) {
        Crew crew = crewRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new IllegalArgumentException("크루의 이메일 혹은 비밀번호가 틀렸습니다."));
        String token = tokenProcessor.createToken(USER, crew.getId());
        return new CrewLoginResponse(token);
    }

    @Transactional
    public void signUp(CrewSignUpRequest crewSignUpRequest) {
        Crew crew = crewSignUpRequest.toCrew();
        crewRepository.save(crew);
    }
}
