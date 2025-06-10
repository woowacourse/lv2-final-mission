package finalmission.umbrella.dto;

import finalmission.umbrella.domain.Umbrella;

public record UmbrellaResponse(long id, String size) {

    public static UmbrellaResponse from(Umbrella umbrella) {
        return new UmbrellaResponse(umbrella.getId(), umbrella.getUmbrellaType().name());
    }
}
