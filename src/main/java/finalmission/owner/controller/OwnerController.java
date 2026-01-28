package finalmission.owner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finalmission.auth.UserId;
import finalmission.owner.dto.OwnerRequest;
import finalmission.owner.service.OwnerService;
import finalmission.shop.dto.ShopResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @UserId Long userId,
            @RequestBody OwnerRequest.Register request
    ) {
        ownerService.register(userId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/shops")
    public ResponseEntity<ShopResponse.Detail> registerShop(
            @UserId Long userId,
            @RequestBody OwnerRequest.Shop request
    ) {
        return ResponseEntity.ok(ownerService.registerShop(userId, request));
    }

    @PutMapping("/shops/{shopId}")
    public ResponseEntity<ShopResponse.Detail> updateShop(
            @UserId Long userId,
            @PathVariable(name = "shopId") Long shopId,
            @RequestBody OwnerRequest.Shop request
    ) {
        return ResponseEntity.ok(ownerService.updateShop(userId, shopId, request));
    }
}
