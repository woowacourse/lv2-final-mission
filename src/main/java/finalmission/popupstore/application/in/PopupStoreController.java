package finalmission.popupstore.application.in;

import finalmission.popupstore.application.PopupStoreService;
import finalmission.popupstore.application.in.dto.OpenPopUpStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "팝업스토어 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/popup-stores")
public class PopupStoreController {

    public final PopupStoreService popupStoreService;

    @Operation(summary = "팝업스토어 생성 API")
    @PostMapping
    public ResponseEntity<Void> open(@RequestBody final OpenPopUpStore request) {
        popupStoreService.open(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
