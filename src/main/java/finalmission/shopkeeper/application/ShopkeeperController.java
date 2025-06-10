package finalmission.shopkeeper.application;

import finalmission.shopkeeper.dto.CreateShopkeeperIn;
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
    public ResponseEntity<Void> signUp(@RequestBody CreateShopkeeperIn request) {
        shopkeeperService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
