package finalmission.controller;

import finalmission.dto.request.StoreCreateRequest;
import finalmission.dto.response.StoreCreateResponse;
import finalmission.infra.auth.AuthenticationPrincipal;
import finalmission.infra.auth.LoginMember;
import finalmission.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreCreateResponse> save(@RequestBody StoreCreateRequest storeCreateRequest,
                                                    @AuthenticationPrincipal LoginMember loginMember) {
        StoreCreateResponse storeCreateResponse = storeService.save(storeCreateRequest, loginMember);
        return ResponseEntity.ok(storeCreateResponse);
    }
}
