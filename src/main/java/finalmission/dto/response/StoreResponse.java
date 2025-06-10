package finalmission.dto.response;

import finalmission.domain.Store;

public record StoreResponse(
        Long id,
        WaitingLineResponse waitingLineResponse
) {

    public static StoreResponse from(Store store) {
        return new StoreResponse(store.getId(), WaitingLineResponse.from(store.getWaitingLine()));
    }
}
