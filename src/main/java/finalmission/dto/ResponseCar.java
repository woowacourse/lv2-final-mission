package finalmission.dto;

import finalmission.domain.Car;

public record ResponseCar(
        Long id,
        String name,
        String licensePlate,
        Long feePerMinute
) {

    public static ResponseCar from(Car car) {
        return new ResponseCar(
                car.getId(),
                car.getName(),
                car.getLicensePlate(),
                car.getFeePerMinute()
        );
    }
}
