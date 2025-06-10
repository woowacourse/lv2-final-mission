package finalmission.umbrella.service;

import finalmission.umbrella.domain.Umbrella;
import finalmission.umbrella.repository.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UmbrellaService {

    private final UmbrellaRepository umbrellaRepository;

    public Umbrella createUmbrella() {
        Umbrella umbrella = Umbrella.create();
        return umbrellaRepository.save(umbrella);
    }
}
