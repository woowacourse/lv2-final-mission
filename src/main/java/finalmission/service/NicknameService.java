package finalmission.service;

import finalmission.domain.Nickname;
import finalmission.service.dto.NicknameGenerateCondition;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NicknameService {

    private final NicknameGenerator nicknameGenerator;

    public NicknameService(NicknameGenerator nicknameGenerator) {
        this.nicknameGenerator = nicknameGenerator;
    }

    public List<Nickname> recommend(NicknameGenerateCondition condition) {
        return nicknameGenerator.generate(condition);
    }
}
