package finalmission.application.service;

import finalmission.application.dto.CrewResponse;
import finalmission.domain.repository.CrewRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CrewService {
    private final CrewRepository crewRepository;

    public CrewService(final CrewRepository crewRepository) {
        this.crewRepository = crewRepository;
    }

    public List<CrewResponse> getCrews() {
        return crewRepository.findAll().stream()
                .map(CrewResponse::new)
                .toList();
    }
}
