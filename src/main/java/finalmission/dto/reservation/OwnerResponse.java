package finalmission.dto.reservation;

import finalmission.domain.reservation.owner.Owner;

public record OwnerResponse(
        String nickname,
        String email,
        String password
) {

    public OwnerResponse(final Owner owner) {
        this(
                owner.getNickname().getValue(),
                owner.getEmail().getValue(),
                owner.getPassword().getValue()
        );
    }
}
