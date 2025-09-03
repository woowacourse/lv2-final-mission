package finalmission.dto.response;

import finalmission.domain.Store;
import finalmission.domain.StoreStatus;

public record StoreCreateResponse(
        Long id,
        String storeName,
        StoreStatus storeStatus
) {

    public static StoreCreateResponse from(final Store store) {
        return new StoreCreateResponse(store.getId(), store.getStoreName(), store.getStoreStatus());
    }
}
