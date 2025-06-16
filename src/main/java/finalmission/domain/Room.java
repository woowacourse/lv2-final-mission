package finalmission.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum Room {
    IMPACT("임팩트"),
    BACKSWING("백스윙"),
    FINISH("피니시"),
    TOPOFSWING("톱오브스윙"),
    ADDRESS("어드레스");

    private final String title;

    Room(final String title) {
        this.title = title;
    }

    private static final Map<String, Room> ROOM_CACHING = new HashMap<>();

    static {
        for (final Room room : values()) {
            ROOM_CACHING.put(room.title, room);
        }
    }

    public static Optional<Room> findByTitle(final String title) {
        return Optional.ofNullable(ROOM_CACHING.get(title));
    }

}
