package finalmission.venue.service.detail;

import finalmission.common.exception.NotFoundException;
import finalmission.venue.domain.Venue;
import finalmission.venue.repository.VenueRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VenueQueryService {

    private final VenueRepository venueRepository;

    public Venue get(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ID에 해당하는 공연장을 찾을 수 없습니다."));
    }

    public List<Venue> getAll() {
        return venueRepository.findAll();
    }
}
