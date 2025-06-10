package finalmission.service;

import finalmission.dto.response.ToiletResponse;
import finalmission.infrastructure.ToiletRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ToiletService {

    private final ToiletRepository toiletRepository;

    public ToiletService(ToiletRepository toiletRepository) {
        this.toiletRepository = toiletRepository;
    }

    public List<ToiletResponse> findAllToilets() {
        return toiletRepository.findAll()
                .stream()
                .map(ToiletResponse::from)
                .toList();
    }
}
