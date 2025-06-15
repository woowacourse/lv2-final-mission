package finalmission.toilet.dto.response;

import finalmission.toilet.domain.Toilet;

public record ToiletResponse(
        Long id,
        String position
) {
    public static ToiletResponse from(Toilet toilet) {
        return new ToiletResponse(toilet.getId(), toilet.getPosition());
    }
}
