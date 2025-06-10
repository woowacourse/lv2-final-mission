package finalmission.shopkeeper.application.in;

import finalmission.shopkeeper.application.ShopkeeperService;
import finalmission.shopkeeper.application.in.dto.CreateShopkeeper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/shopkeepers")
@RestController
public class ShopkeeperController {

    private final ShopkeeperService shopkeeperService;

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody CreateShopkeeper request) {
        shopkeeperService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
