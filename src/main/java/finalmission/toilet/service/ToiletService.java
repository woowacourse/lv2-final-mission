package finalmission.toilet.service;

import finalmission.toilet.dto.response.ToiletResponse;
import finalmission.toilet.infrastructure.ToiletRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ToiletService {

    private final ToiletRepository toiletRepository;

    public ToiletService(ToiletRepository toiletRepository) {
        this.toiletRepository = toiletRepository;
    }

    public List<ToiletResponse> findToilets() {
        return toiletRepository.findAll()
                .stream()
                .map(ToiletResponse::from)
                .toList();
    }
}
