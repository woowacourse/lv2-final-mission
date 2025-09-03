package finalmission.controller;

import finalmission.dto.TourCreateRequest;
import finalmission.dto.TourResponse;
import finalmission.service.TourService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping
    public ResponseEntity<List<TourResponse>> findAllTour() {
        List<TourResponse> allTours = tourService.getAllTours();
        return ResponseEntity.ok(allTours);
    }

    @PostMapping("/agency")
    public ResponseEntity<TourResponse> createTour(@RequestBody TourCreateRequest request) {
        TourResponse saved = tourService.createTour(request);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/agency/{tourId}")
    public ResponseEntity<Void> removeTour(@PathVariable("tourId") Long tourId) {
        tourService.deleteTour(tourId);
        return ResponseEntity.noContent().build();
    }
}
