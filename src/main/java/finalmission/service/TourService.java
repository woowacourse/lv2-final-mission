package finalmission.service;

import finalmission.domain.entity.Tour;
import finalmission.dto.TourCreateRequest;
import finalmission.dto.TourResponse;
import finalmission.repository.TourRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    @Transactional(readOnly = true)
    public List<TourResponse> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        List<TourResponse> responses = new ArrayList<>();
        for (Tour tour : tours) {
            responses.add(TourResponse.from(tour));
        }
        return responses;
    }

    public TourResponse createTour(TourCreateRequest request) {
        Tour saved = tourRepository.save(new Tour(
                request.title(),
                request.description()
        ));
        return TourResponse.from(saved);
    }

    public void deleteTour(Long tourId) {
        tourRepository.deleteById(tourId);
    }
}
