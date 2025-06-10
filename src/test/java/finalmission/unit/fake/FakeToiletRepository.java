package finalmission.unit.fake;

import finalmission.domain.Toilet;
import finalmission.infrastructure.ToiletRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeToiletRepository implements ToiletRepository {

    private final List<Toilet> toilets = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public Toilet save(Toilet toilet) {
        Toilet newToilet = new Toilet(index.getAndIncrement(), toilet.getPosition());
        toilets.add(newToilet);
        return newToilet;
    }

    @Override
    public List<Toilet> findAll() {
        return toilets;
    }

    @Override
    public Optional<Toilet> findById(Long id) {
        return toilets.stream()
                .filter(toilet -> toilet.getId().equals(id))
                .findFirst();
    }
}
