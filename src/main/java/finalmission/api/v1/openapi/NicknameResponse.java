package finalmission.api.v1.openapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;

public record NicknameResponse(String nickname) {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public NicknameResponse(List<String> rawList) {
        this(rawList != null && !rawList.isEmpty()
                ? rawList.get(0)
                : "");
    }
}
