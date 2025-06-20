package finalmission.domain;

import finalmission.exception.BadRequestException;
import finalmission.exception.ErrorCode;
import finalmission.global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int maxNumberOfPeople;

    public Room(final String name, final int maxNumberOfPeople) {
        this.name = name;
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public void validateNumberOfPeople(final int numberOfPeople) {
        if (numberOfPeople > maxNumberOfPeople) {
            throw new BadRequestException(ErrorCode.EXCEEDED_ROOM_CAPACITY);
        }
    }
}
