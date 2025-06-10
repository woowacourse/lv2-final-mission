package finalmission.client.dto;

import java.time.LocalDate;

public record SpcdeInfoResponse(
        String resultCode,
        String resultMsg,
        LocalDate locdate,
        Boolean isHoliday
) {
}
