package finalmission.room.repository;

import finalmission.room.domain.Room;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends ListCrudRepository<Room, Long> {
}
