package finalmission.music.service;

import finalmission.music.controller.dto.AlbumRequest;
import finalmission.music.controller.dto.AlbumResponse;
import finalmission.music.domain.Album;
import finalmission.music.domain.spotify.SimplifiedAlbumObject;
import finalmission.music.domain.spotify.SpotifyAlbums;
import finalmission.music.repository.AlbumRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final SpotifySearchService spotifySearchService;

    public AlbumResponse save(final AlbumRequest request) {
        SpotifyAlbums albums = spotifySearchService.search(request.name());
        SimplifiedAlbumObject item = albums.items()[0];

        Album album = albumRepository.save(new Album(
            item.name(),
            item.artists()[0].name(),
            item.total_tracks(),
            LocalDate.parse(item.release_date()),
            item.id()));

        return AlbumResponse.from(album);
    }
}
