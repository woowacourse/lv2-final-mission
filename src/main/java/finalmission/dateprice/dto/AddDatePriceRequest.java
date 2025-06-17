package finalmission.dateprice.dto;

import java.time.LocalDate;

public record AddDatePriceRequest(LocalDate date,
                                  long price,
                                  long accommodationId) {
}
