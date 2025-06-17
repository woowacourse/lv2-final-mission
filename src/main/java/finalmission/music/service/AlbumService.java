package finalmission.music.service;

import finalmission.music.controller.dto.AlbumRequest;
import finalmission.music.controller.dto.AlbumResponse;
import finalmission.music.domain.Album;
import finalmission.music.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumResponse create(final AlbumRequest request) {
        Album album = albumRepository.save(
            new Album(
                request.name(),
                request.artistName(),
                request.totalTracks(),
                request.releaseDate(),
                request.spotifyId()
            ));

        return AlbumResponse.from(album);
    }

    public void delete(final Long id) {
        albumRepository.deleteById(id);
    }

    public AlbumResponse getAlbum(final Long id) {
        Album album = albumRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 앨범이 존재하지 않습니다."));

        return AlbumResponse.from(album);
    }
}
