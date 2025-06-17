package finalmission.randomname;

import finalmission.randomname.service.RandomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/random-name")
public class RandomNameController {

    private final RandomService randomService;

    public RandomNameController(RandomService randomService) {
        this.randomService = randomService;
    }

    @GetMapping
    public ResponseEntity<String> createRandomName() {
        String randomName = randomService.createRandomName();
        return ResponseEntity.ok(randomName);
    }
}
