package finalmission.reservation.dto;

import finalmission.umbrella.domain.UmbrellaType;

public record AvailableReservationCount(UmbrellaType umbrellaType, long count) {
}
