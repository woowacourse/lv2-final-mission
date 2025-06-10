package finalmission.presentation;

import finalmission.dto.ToiletResponse;
import finalmission.service.ToiletService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/toilets")
public class ToiletController {

    private final ToiletService toiletService;

    public ToiletController(ToiletService toiletService) {
        this.toiletService = toiletService;
    }

    @GetMapping
    public List<ToiletResponse> getAllToilets() {
        return toiletService.findAllToilets();
    }
}
