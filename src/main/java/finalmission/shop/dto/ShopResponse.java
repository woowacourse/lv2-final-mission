package finalmission.shop.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import finalmission.shop.domain.OperatingHour;
import finalmission.shop.domain.Shop;
import finalmission.shop.domain.ShopType;

public class ShopResponse {

    private ShopResponse() {
    }

    public record Simple(
            Long id,
            String name,
            ShopType type
    ) {

        public Simple(Shop shop) {
            this(shop.getId(), shop.getName(), shop.getType());
        }
    }

    public record Detail(
            Long id,
            String name,
            ShopType type,
            String detail,
            List<InnerOperatingHour> innerOperatingHours
    ) {

        public Detail(Shop shop) {
            this(shop.getId(), shop.getName(), shop.getType(), shop.getDetail(),
                    InnerOperatingHour.of(shop.getOperatingHours()));
        }

        public record InnerOperatingHour(
                DayOfWeek dayOfWeek,
                List<LocalTime> time
        ) {

            public static List<InnerOperatingHour> of(List<OperatingHour> operatingHours) {
                Map<DayOfWeek, List<LocalTime>> map = operatingHours.stream()
                        .collect(Collectors.toMap(
                                operatingHour -> operatingHour.getDayOfWeek(),
                                operatingHour -> new ArrayList<>(Arrays.asList(operatingHour.getTime())),
                                (oldVal, newVal) -> {
                                    oldVal.addAll(newVal);
                                    return oldVal;
                                }
                        ));

                return Arrays.stream(DayOfWeek.values())
                        .filter(map::containsKey)
                        .map(dayOfWeek -> new InnerOperatingHour(dayOfWeek, map.get(dayOfWeek)))
                        .toList();
            }
        }
    }
}
