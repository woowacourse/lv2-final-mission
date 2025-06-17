package finalmission.room.exception;

import finalmission.global.exception.BadRequestException;

public class RoomBadRequestException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "회의실에 대해 잘못된 요청입니다.";

    public RoomBadRequestException(String message) {
        super(message);
    }

    public RoomBadRequestException() {
        this(DEFAULT_MESSAGE);
    }
}
