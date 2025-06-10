package finalmission.dto;

import finalmission.domain.Toilet;

public record ToiletResponse(
        Long id,
        String position
) {
    public static ToiletResponse from(Toilet toilet) {
        return new ToiletResponse(toilet.getId(), toilet.getPosition());
    }
}
