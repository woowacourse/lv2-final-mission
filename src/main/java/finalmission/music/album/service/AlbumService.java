package finalmission.music.album.service;

import finalmission.music.album.controller.dto.AlbumRequest;
import finalmission.music.album.controller.dto.AlbumResponse;
import finalmission.music.album.domain.Album;
import finalmission.music.album.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumResponse create(final AlbumRequest request) {
        validateIsDuplicatedAlbum(request.name(), request.artistName());

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

    private void validateIsDuplicatedAlbum(final String name, final String artistName) {
        if (albumRepository.existsByNameAndArtistName(name, artistName)) {
            throw new IllegalArgumentException("[ERROR] 앨범을 중복 등록 할 수 없습니다.");
        }
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
