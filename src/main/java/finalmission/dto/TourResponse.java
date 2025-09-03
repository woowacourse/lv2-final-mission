package finalmission.dto;

import finalmission.domain.entity.Tour;
import java.util.Arrays;
import java.util.List;

public record TourResponse(Long tourId, String title, String description) {

    public static TourResponse from(Tour tour) {
        return new TourResponse(
                tour.getId(),
                tour.getTitle(),
                tour.getDescription()
        );
    }

    public static List<TourResponse> from(Tour... tours) {
        return Arrays.stream(tours)
                .map(TourResponse::from)
                .toList();
    }
}
