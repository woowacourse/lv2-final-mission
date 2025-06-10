package finalmission.application;

import finalmission.domain.Address;
import finalmission.domain.Gym;
import finalmission.domain.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository repository;

    public void register(final String name, final Address address) {
        var gym = new Gym(name, address);
        repository.save(gym);
    }
}
