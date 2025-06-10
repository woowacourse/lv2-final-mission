package finalmission.controller;

import finalmission.domain.Nickname;
import finalmission.service.NicknameService;
import finalmission.service.dto.NicknameGenerateCondition;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/nicknames")
@RestController
public class NicknameController {

    private final NicknameService nicknameService;

    public NicknameController(NicknameService nicknameService) {
        this.nicknameService = nicknameService;
    }

    @PostMapping("/recommend")
    public ResponseEntity<List<Nickname>> recommendNicknames(@RequestBody NicknameGenerateCondition condition) {
        List<Nickname> response = nicknameService.recommend(condition);
        return ResponseEntity.ok(response);
    }
}
