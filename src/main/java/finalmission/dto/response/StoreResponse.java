package finalmission.dto.response;

import finalmission.domain.Store;
import finalmission.domain.StoreStatus;

public record StoreResponse(
        Long id,
        String storeName,
        StoreStatus storeStatus,
        String description,
        Double starRating,
        MemberResponse owner,
        WaitingLineResponse waitingLine
) {
    public static StoreResponse from(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getStoreName(),
                store.getStoreStatus(),
                store.getDescription(),
                store.getStarRating(),
                MemberResponse.from(store.getMember()),
                WaitingLineResponse.from(store.getWaitingLine())
        );
    }
}
