package finalmission.application;

import finalmission.domain.Address;
import finalmission.domain.Gym;
import finalmission.domain.GymRepository;
import org.springframework.stereotype.Service;

@Service
public class GymService {

    private final GymRepository repository;

    public GymService(final GymRepository repository) {
        this.repository = repository;
    }

    public void register(final String name, final Address address) {
        var gym = new Gym(name, address);
        repository.save(gym);
    }
}
