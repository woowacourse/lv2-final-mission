package library.reservation.dto;

import java.time.LocalDate;
import library.collection.domain.Collection;
import library.collection.domain.CollectionStatus;


public record CollectionReservationResponse(
        Long id,
        CollectionStatus collectionStatus,
        String location,
        LocalDate  dueDate

) {
    public static CollectionReservationResponse from(final Collection collection) {
        if (collection.getBorrow() == null) {
            return new CollectionReservationResponse(collection.getId(), collection.getCollectionStatus(),
                    collection.getLocation(), null);
        }
        return new CollectionReservationResponse(collection.getId(), collection.getCollectionStatus(),
                collection.getLocation(), collection.getBorrow().getDueDate());
    }
}
