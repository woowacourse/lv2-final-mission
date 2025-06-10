package finalmission.music.controller;

import finalmission.music.controller.dto.AlbumRequest;
import finalmission.music.controller.dto.AlbumResponse;
import finalmission.music.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping("/albums")
    public ResponseEntity<AlbumResponse> save(@RequestBody final AlbumRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(albumService.save(request));
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumResponse> getAlbum(@PathVariable("id") Long id) {
        return ResponseEntity.ok(albumService.getAlbum(id));
    }

    @DeleteMapping("/albums/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        albumService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
