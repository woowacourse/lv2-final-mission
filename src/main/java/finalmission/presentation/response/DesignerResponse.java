package finalmission.presentation.response;

import finalmission.domain.designer.Designer;
import java.time.DayOfWeek;

public record DesignerResponse(
        Long designerId,
        String name,
        DayOfWeek offDay
) {

    public static DesignerResponse fromDesigner(final Designer designer) {
        return new DesignerResponse(
                designer.getDesignerId(),
                designer.getName(),
                designer.getOffDay()
        );
    }
}
