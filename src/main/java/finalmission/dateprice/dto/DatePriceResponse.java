package finalmission.dateprice.dto;

import finalmission.accommodation.dto.AccommodationResponse;
import finalmission.dateprice.domain.DatePrice;
import java.time.LocalDate;

public record DatePriceResponse(long id,
                                LocalDate date,
                                long price,
                                AccommodationResponse accommodation) {
    
    public static DatePriceResponse of(DatePrice datePrice) {
        return new DatePriceResponse(
                datePrice.getId(),
                datePrice.getDate(),
                datePrice.getPrice(),
                AccommodationResponse.of(datePrice.getAccommodation())
        );
    }
}
