package finalmission.toilet.presentation;

import finalmission.toilet.dto.response.ToiletResponse;
import finalmission.toilet.service.ToiletService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toilets")
public class ToiletController {

    private final ToiletService toiletService;

    public ToiletController(ToiletService toiletService) {
        this.toiletService = toiletService;
    }

    @GetMapping
    public List<ToiletResponse> getToilets() {
        return toiletService.findToilets();
    }
}
