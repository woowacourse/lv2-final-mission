package finalmission.service;

import finalmission.domain.Nickname;
import finalmission.service.dto.NicknameGenerateCondition;
import java.util.List;

public interface NicknameGenerator {
    List<Nickname> generate(NicknameGenerateCondition condition);
}
