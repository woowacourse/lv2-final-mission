package finalmission.cake.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record CakeReservationRequest(
        @JsonFormat(pattern = "yyyy.MM.dd")
        LocalDate date,
        Long timeId,
        Long cakeId,
        Long flavorId,
        Long sizeId,
        String lettering
) {
}
