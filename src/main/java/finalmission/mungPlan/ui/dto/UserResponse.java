package finalmission.mungPlan.ui.dto;

import finalmission.mungPlan.domain.User;

public record UserResponse(
    String id,
    String name
) {

    public UserResponse(User user) {
        this(user.getId(), user.getName());
    }

}
