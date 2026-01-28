package finalmission.owner.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import finalmission.shop.domain.ShopType;

public class OwnerRequest {

    public record Register(
            String businessLicenseUrl,
            String businessRegistrationNumber
    ) {

    }

    public record Shop(
        String name,
        ShopType type,
        String detail,
        List<OperatingHour> operatingHours
    ) {

        public record OperatingHour(
                DayOfWeek dayOfWeek,
                LocalTime time
        ) {

        }
    }
}
