package finalmission.dto;

public record RequestRegisterCar(
        String name,
        String licensePlate,
        Long feePerHour
) {
}
