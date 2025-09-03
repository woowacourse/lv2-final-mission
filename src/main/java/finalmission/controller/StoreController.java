package finalmission.controller;

import finalmission.dto.request.AddWaitingRequest;
import finalmission.dto.request.ChangeStoreStatusRequest;
import finalmission.dto.request.StoreCreateRequest;
import finalmission.dto.response.AddWaitingResponse;
import finalmission.dto.response.RankResponse;
import finalmission.dto.response.StoreCreateResponse;
import finalmission.dto.response.StoreResponse;
import finalmission.infra.auth.AuthenticationPrincipal;
import finalmission.infra.auth.LoginMember;
import finalmission.service.StoreService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreCreateResponse> createStore(@RequestBody StoreCreateRequest storeCreateRequest,
                                                           @AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok(storeService.createStore(storeCreateRequest, loginMember));
    }

    @GetMapping
    public ResponseEntity<List<StoreResponse>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/open")
    public ResponseEntity<List<StoreResponse>> getAllOpenedStores() {
        return ResponseEntity.ok(storeService.getAllOpenedStores());
    }

    @PatchMapping("/{storeId}/status")
    public ResponseEntity<Void> updateStoreStatus(
            @PathVariable Long storeId,
            @RequestBody ChangeStoreStatusRequest changeStoreStatusRequest,
            @AuthenticationPrincipal LoginMember loginMember) {
        storeService.updateStoreStatus(storeId, changeStoreStatusRequest.status(), loginMember);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/waiting")
    public ResponseEntity<AddWaitingResponse> addWaiting(
            @RequestBody AddWaitingRequest addWaitingRequest,
            @AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok(storeService.addWaiting(addWaitingRequest, loginMember));
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> removeWaiting(
            @PathVariable Long storeId,
            @AuthenticationPrincipal LoginMember loginMember) {
        storeService.removeWaiting(storeId, loginMember);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{storeId}/waiting/rank")
    public ResponseEntity<RankResponse> getWaitingRank(
            @PathVariable Long storeId,
            @AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok(storeService.getWaitingRank(storeId, loginMember));
    }
}
