package finalmission.party.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PartyStatus {

    OPEN("진행 중"),
    CLOSED("마감"),
    ;

    private final String description;

    public boolean isOpen() {
        return this == OPEN;
    }

    public boolean isClosed() {
        return this == CLOSED;
    }
}
