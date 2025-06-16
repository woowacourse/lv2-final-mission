package finalmission.application.service;

import finalmission.application.dto.CoachResponse;
import finalmission.domain.repository.CoachRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CoachService {
    private final CoachRepository coachRepository;

    public CoachService(final CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public List<CoachResponse> getCoaches() {
        return coachRepository.findAll().stream()
                .map(CoachResponse::new)
                .toList();
    }
}
