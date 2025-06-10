package finalmission.presentation.response;

import finalmission.domain.design.Design;

public record DesignResponse(
        Long designId,
        String name,
        int price,
        int turnaroundTime
) {

    public static DesignResponse fromDesign(final Design design) {
        return new DesignResponse(
                design.getDesignId(),
                design.getName(),
                design.getPrice(),
                design.getTurnaroundTime()
        );
    }
}
