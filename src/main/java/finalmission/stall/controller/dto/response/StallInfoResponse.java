package finalmission.stall.controller.dto.response;

import finalmission.stall.entity.Stall;
import finalmission.stallstatus.controller.dto.response.StallStatusFindResponse;
import finalmission.stallstatus.entity.StallStatus;

import java.util.List;

public record StallInfoResponse(
        String name,
        List<StallStatusFindResponse> stallStatusFindResponses
) {
    public static StallInfoResponse from(Stall stall) {
        List<StallStatus> stallStatuses = stall.getStallStatuses();
        List<StallStatusFindResponse> stallStatusFindResponses = stallStatuses.stream().map(StallStatusFindResponse::from).toList();
        return new StallInfoResponse(stall.getName(), stallStatusFindResponses);
    }
}
