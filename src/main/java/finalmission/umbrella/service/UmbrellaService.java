package finalmission.umbrella.service;

import finalmission.umbrella.domain.Umbrella;
import finalmission.umbrella.dto.response.UmbrellaResponse;
import finalmission.umbrella.repository.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UmbrellaService {

    private final UmbrellaRepository umbrellaRepository;

    public UmbrellaResponse createUmbrella() {
        Umbrella umbrella = Umbrella.create();
        Umbrella saveUmbrella = umbrellaRepository.save(umbrella);
        return new UmbrellaResponse(saveUmbrella.getId());
    }
}
