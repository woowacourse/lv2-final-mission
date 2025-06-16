package finalmission.ballparkreservation.external.dto;

import java.util.List;

public record WeatherResponse(
        Response response
) {
    public boolean isRaining() {
        Response.Body.Items.Item rainyItem = response.body.items.item.stream()
                .filter(item -> item.category.equals("POP"))
                .findAny()
                .orElseThrow(RuntimeException::new);

        try {
            Float factor = Float.valueOf(rainyItem.fcstValue);
            return factor > 0.0f;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<Response.Body.Items.Item> getItems() {
        return response.body().items().item;
    }

    record Response(
            Body body
    ) {
        record Body(
                Items items
        ) {
            record Items(
                    List<Item> item
            ) {
                record Item(
                        String category,
                        String fcstValue
                ) {

                }
            }
        }
    }
}
