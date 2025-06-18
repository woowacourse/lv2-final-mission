package finalmission.dto.request;

public record BookRequest(
        String name,
        String author,
        Long category_id,
        int inventory,
        int period
) {
}
