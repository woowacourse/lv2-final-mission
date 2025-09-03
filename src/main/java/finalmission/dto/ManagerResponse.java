package finalmission.dto;

import finalmission.domain.entity.Manager;
import java.util.Arrays;
import java.util.List;

public record ManagerResponse(Long managerId, String name, String phoneNumber) {

    public static ManagerResponse from(Manager manager) {
        return new ManagerResponse(
                manager.getId(),
                manager.getName(),
                manager.getPhoneNumber()
        );
    }

    public static List<ManagerResponse> from(Manager... managers) {
        return Arrays.stream(managers)
                .map(ManagerResponse::from)
                .toList();
    }
}
