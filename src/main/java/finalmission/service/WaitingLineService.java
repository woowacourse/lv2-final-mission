package finalmission.service;

import finalmission.domain.WaitingLine;
import finalmission.dto.response.WaitingLineCreateResponse;
import finalmission.repository.WaitingLineRepository;
import org.springframework.stereotype.Service;

@Service
public class WaitingLineService {

    private final WaitingLineRepository waitingLineRepository;

    public WaitingLineService(final WaitingLineRepository waitingLineRepository) {
        this.waitingLineRepository = waitingLineRepository;
    }

    public WaitingLineCreateResponse save(WaitingLine waitingLine) {
        return WaitingLineCreateResponse.from(waitingLineRepository.save(waitingLine));
    }
}
