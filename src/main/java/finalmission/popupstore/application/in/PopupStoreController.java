package finalmission.popupstore.application.in;

import finalmission.popupstore.application.PopupStoreService;
import finalmission.popupstore.application.in.dto.CreatePopUpStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/popup-stores")
public class PopupStoreController {

    public final PopupStoreService popupStoreService;

    @PostMapping
    public ResponseEntity<Void> open(@RequestBody final CreatePopUpStore request) {
        popupStoreService.open(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
