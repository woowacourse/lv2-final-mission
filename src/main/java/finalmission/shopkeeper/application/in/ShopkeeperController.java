package finalmission.shopkeeper.application.in;

import finalmission.shopkeeper.application.ShopkeeperService;
import finalmission.shopkeeper.application.in.dto.SignupShopkeeper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "점주 API")
@RequiredArgsConstructor
@RequestMapping("/shopkeepers")
@RestController
public class ShopkeeperController {

    private final ShopkeeperService shopkeeperService;

    @Operation(summary = "점주 등록 API")
    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody SignupShopkeeper request) {
        shopkeeperService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
