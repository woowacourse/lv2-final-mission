package finalmission.apply.application;

import finalmission.apply.domain.Apply;
import finalmission.apply.infrastructure.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplyDeleter {

    private final ApplyRepository applyRepository;

    public void execute(final Apply apply) {
        applyRepository.delete(apply);
    }

    public void execute(final Long id) {
        applyRepository.deleteById(id);
    }
}
