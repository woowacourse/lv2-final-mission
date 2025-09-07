package lavatoryreservation.lavatory.service;

import java.util.Optional;
import lavatoryreservation.exception.LavatoryException;
import lavatoryreservation.lavatory.domain.Lavatory;
import lavatoryreservation.lavatory.repository.LavatoryRepository;
import org.springframework.stereotype.Service;

@Service
public class LavatoryService {

    private final LavatoryRepository lavatoryRepository;

    public LavatoryService(LavatoryRepository lavatoryRepository) {
        this.lavatoryRepository = lavatoryRepository;
    }

    public Lavatory addLavatory(Lavatory lavatory) {
        validateDuplicateLavatory(lavatory);
        return lavatoryRepository.save(lavatory);
    }

    private void validateDuplicateLavatory(Lavatory lavatory) {
        if (lavatoryRepository.existsByDescription(lavatory.getDescription())) {
            throw new LavatoryException("중복된 화장실 설명입니다");
        }
    }

    public Optional<Lavatory> findById(Long id) {
        return lavatoryRepository.findById(id);
    }

    public Lavatory getById(Long lavatoryId) {
        return lavatoryRepository.findById(lavatoryId)
                .orElseThrow(() -> new LavatoryException("존재하지 않는 화장실 입니다."));
    }
}
