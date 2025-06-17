package finalmission.accommodation.dto;

import finalmission.accommodation.domain.Accommodation;

public record AccommodationResponse(long id,
                                    String name,
                                    String description,
                                    String address) {

    public static AccommodationResponse of(Accommodation accommodation) {
        return new AccommodationResponse(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getDescription(),
                accommodation.getAddress()
        );
    }
}
